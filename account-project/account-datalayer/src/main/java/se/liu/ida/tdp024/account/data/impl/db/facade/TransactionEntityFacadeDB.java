package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public long create(String transactionType, int amount, long accountID) {
        EntityManager em = EMF.getEntityManager();

        try {
            Account account = em.find(AccountDB.class, accountID);
            em.getTransaction().begin();

            Transaction transaction = new TransactionDB();

            transaction.setTransactionType(transactionType);
            transaction.setAmount(amount);
            transaction.setTimestamp(new Date());
            if (transactionType == "CREDIT") {
                account.setHoldnings(account.getHoldings() + amount);
                transaction.setStatus("OK");
            }
            if (transactionType == "DEBIT") {
                if (account.getHoldings() >= amount) {
                    account.setHoldnings(account.getHoldings() - amount);
                    transaction.setStatus("OK");

                } else {
                    System.out.println("Insufficient funds");
                    transaction.setStatus("FAILED");

                }
            }
            if (transactionType != "CREDIT" && transactionType != "DEBIT") {
                System.out.println(transactionType + " is not a valid transaction type");
                transaction.setStatus("FAILED");
            }
            transaction.setAccount(account);

            em.persist(transaction);
            em.getTransaction().commit();

            return transaction.getID();

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
    public Transaction find(long transactionID) {
        EntityManager em = EMF.getEntityManager();

        try {
            return em.find(TransactionDB.class, transactionID);

        } catch (Exception e) {
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> findByPerson(long accountID) {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account.id = :accountID");
            query.setParameter("accountID", accountID);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> findAll() {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM TransactionDB t");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }

    }

}
