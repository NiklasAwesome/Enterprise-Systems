package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;
import se.liu.ida.tdp024.account.utils.api.logger.AccountLogger;
import se.liu.ida.tdp024.account.utils.impl.helpers.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.utils.impl.logger.AccountLoggerKafka;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    AccountLogger al = new AccountLoggerKafka("transaction");
    AccountJsonSerializer ajs = new AccountJsonSerializerImpl();

    @Override
    public long create(String type, int amount, Account account, boolean success, EntityManager em)
            throws AccountServiceConfigurationException, AccountInputParameterException {

        try {
            if (type == null || type.isEmpty() || account == null || em == null) {
                throw new AccountInputParameterException("Invalid parameters");
            }

            Transaction transaction = new TransactionDB();

            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setCreated(new Date());
            transaction.setStatus(success ? "OK" : "FAILED");
            transaction.setAccount(account);

            em.persist(transaction);
            al.log("Transaction: " + ajs.toJson(transaction));
            return transaction.getID();
        } catch (AccountInputParameterException e) {
            throw e;
        } catch (TransactionRequiredException | IllegalArgumentException e) {
            throw new AccountServiceConfigurationException(
                    "Error creating transaction due to internal error: " + e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Transaction find(long transactionID)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Transaction transaction = em.find(TransactionDB.class, transactionID);
            if (transaction == null) {
                throw new AccountEntityNotFoundException(
                        String.format("The transaction with ID: %s was not found", transactionID));
            }
            return transaction;
        } catch (AccountEntityNotFoundException e) {
            throw e;

        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException(
                    "Error finding transaction due to internal error: " + e.getMessage());

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
    public List<Transaction> findByPerson(long accountID)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account.id = :accountID");
            query.setParameter("accountID", accountID);
            if (query.getResultList().isEmpty()) {
                throw new AccountEntityNotFoundException(
                        String.format("The transactions with accountID: %s was not found", accountID));
            }
            return query.getResultList();
        } catch (AccountEntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException(
                    "Error finding transaction due to internal error: " + e.getMessage());
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
    public List<Transaction> findAll() throws AccountServiceConfigurationException, AccountEntityNotFoundException {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM TransactionDB t");
            if (query.getResultList().isEmpty()) {
                throw new AccountEntityNotFoundException("No transactions was not found");
            }
            return query.getResultList();
        } catch (AccountEntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new AccountServiceConfigurationException(
                    "Error finding transaction due to internal error: " + e.getMessage());
        } catch (Exception e) {
            throw e;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

}
