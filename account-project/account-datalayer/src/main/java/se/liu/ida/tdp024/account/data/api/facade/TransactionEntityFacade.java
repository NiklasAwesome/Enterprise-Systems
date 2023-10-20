package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import javax.persistence.EntityManager;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;

public interface TransactionEntityFacade {
    public long create(String transactionType, int amount, Account account, boolean success, EntityManager em)
            throws AccountServiceConfigurationException, AccountInputParameterException;

    public Transaction find(long transactionID)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException;

    public List<Transaction> findByPerson(long accountID)
            throws AccountServiceConfigurationException, AccountEntityNotFoundException;

    public List<Transaction> findAll() throws AccountServiceConfigurationException, AccountEntityNotFoundException;
}
