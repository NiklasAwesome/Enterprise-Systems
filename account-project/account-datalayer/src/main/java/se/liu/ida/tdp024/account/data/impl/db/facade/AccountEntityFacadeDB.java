package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private TransactionEntityFacade transactionEntityFacade;

    public AccountEntityFacadeDB(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public long create(String bankKey, String personKey, String accountType, int holdings) {
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();

            Account account = new AccountDB();

            account.setBankKey(bankKey);
            account.setPersonKey(personKey);
            account.setAccountType(accountType);
            account.setHoldnings(holdings);

            em.persist(account);
            em.getTransaction().commit();

            return account.getID();

        } catch (Exception e) {
            return 0;

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Account find(long id) {
        EntityManager em = EMF.getEntityManager();

        try {
            return em.find(AccountDB.class, id);

        } catch (Exception e) {
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Account> findByPerson(String personKey) {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM AccountDB t WHERE t.personKey = :personKey");
            query.setParameter("personKey", personKey);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Account> findAll() {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT a FROM AccountDB a");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }

    }

    @Override
    public long debit(long accountID, int amount) {
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, accountID, LockModeType.PESSIMISTIC_READ);

            boolean sufficentHoldings = account.getHoldings() >= amount;

            if (sufficentHoldings) {
                account.setHoldnings(account.getHoldings() - amount);
                em.merge(account);
            }

            long transactionID = transactionEntityFacade.create("DEBIT", amount, account, sufficentHoldings, em);

            if (transactionID == 0) {
                em.close();
                return 0;
            }
            em.getTransaction().commit();
            return transactionID;

        } catch (Exception e) {
            return 0;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public long credit(long accountID, int amount) {
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, accountID, LockModeType.PESSIMISTIC_READ);

            account.setHoldnings(account.getHoldings() + amount);
            em.merge(account);

            long transactionID = transactionEntityFacade.create("CREDIT", amount, account, true, em);
            if (transactionID == 0) {
                em.close();
                return 0;
            }
            em.getTransaction().commit();
            return transactionID;

        } catch (Exception e) {
            return 0;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public TransactionEntityFacade getTransactionEntityFacade() {
        return this.transactionEntityFacade;
    }

}
