package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;

public interface AccountEntityFacade {
    public long create(String bankKey, String personKey, String accountType, int holdings) throws AccountServiceConfigurationException, AccountInputParameterException;

    public Account find(long id) throws AccountServiceConfigurationException, AccountEntityNotFoundException;

    public List<Account> findByPerson(String personKey) throws AccountServiceConfigurationException, AccountEntityNotFoundException;

    public List<Account> findAll() throws AccountServiceConfigurationException, AccountEntityNotFoundException;

    public long debit(long id, int amount) throws AccountServiceConfigurationException, AccountEntityNotFoundException, AccountInsufficentHoldingsException, AccountInputParameterException;

    public long credit(long id, int amount) throws AccountServiceConfigurationException, AccountEntityNotFoundException, AccountInputParameterException;

    public TransactionEntityFacade getTransactionEntityFacade();
}
