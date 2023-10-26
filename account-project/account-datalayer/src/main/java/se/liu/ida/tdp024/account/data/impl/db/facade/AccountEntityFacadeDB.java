package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private TransactionEntityFacade transactionEntityFacade;

    public AccountEntityFacadeDB(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public long create(String bankKey, String personKey, String accountType, int holdings)
            throws AccountServiceConfigurationException, AccountInputParameterException {
        EntityManager em = EMF.getEntityManager();

        try {
            if (bankKey == null || bankKey.isEmpty() || personKey == null || personKey.isEmpty() || accountType == null
                    || accountType.isEmpty()) {
                throw new AccountInputParameterException("emptyInput");
            }
            em.getTransaction().begin();

            Account account = new AccountDB();

            account.setBankKey(bankKey);
            account.setPersonKey(personKey);
            account.setAccountType(accountType);
            account.setHoldnings(holdings);

            em.persist(account);
            em.getTransaction().commit();

            return account.getID();

        } catch (AccountInputParameterException e) {
            throw e;

        } catch (IllegalStateException | RollbackException | IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountCreate");

        } catch (Exception e) {
            throw e;

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Account find(long id) throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Account account = em.find(AccountDB.class, id);
            if (account == null) {
                throw new AccountEntityNotFoundException("account");
            }
            return account;

        } catch (AccountEntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountFind");
        } catch (Exception e) {
            throw e;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Account> findByPerson(String personKey)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM AccountDB t WHERE t.personKey = :personKey");
            query.setParameter("personKey", personKey);
            if (query.getResultList().isEmpty()) {
                throw new AccountEntityNotFoundException("account");
            }
            return query.getResultList();
        } catch (AccountEntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountFindByPerson");
        } catch (Exception e) {
            throw e;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Account> findAll() throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT a FROM AccountDB a");
            List<Account> l = query.getResultList();
            if (l.isEmpty()) {
                throw new AccountEntityNotFoundException("account");
            }
            return l;
        } catch (AccountEntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountFindAll");
        } catch (Exception e) {
            throw e;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }

    }

    @Override
    public long debit(long accountID, int amount) throws AccountServiceConfigurationException,
            AccountEntityNotFoundException, AccountInsufficentHoldingsException, AccountInputParameterException {
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, accountID, LockModeType.PESSIMISTIC_READ);
            if (account == null) {
                throw new AccountEntityNotFoundException("account");
            }
            boolean sufficentHoldings = account.getHoldings() >= amount;

            if (sufficentHoldings) {
                account.setHoldnings(account.getHoldings() - amount);
                em.merge(account);
            }

            long transactionID = transactionEntityFacade.create("DEBIT", amount, account, sufficentHoldings, em);

            em.getTransaction().commit();
            if (!sufficentHoldings) {
                throw new AccountInsufficentHoldingsException("insufficent");
            }
            return transactionID;

        } catch (AccountEntityNotFoundException | AccountInsufficentHoldingsException | AccountInputParameterException e) {
            throw e;
        } catch (IllegalStateException | RollbackException | IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountDebit");

        } catch (Exception e) {
            throw e;

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public long credit(long accountID, int amount)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException, AccountInputParameterException{
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, accountID, LockModeType.PESSIMISTIC_READ);
            if (account == null) {
                throw new AccountEntityNotFoundException("account");
            }
            account.setHoldnings(account.getHoldings() + amount);
            em.merge(account);

            long transactionID = transactionEntityFacade.create("CREDIT", amount, account, true, em);

            em.getTransaction().commit();
            return transactionID;

        } catch (AccountEntityNotFoundException | AccountInputParameterException e) {
            throw e;
        } catch (IllegalStateException | RollbackException | IllegalArgumentException e) {
            throw new AccountServiceConfigurationException("accountCredit");
        } catch (Exception e) {
            throw e;
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
