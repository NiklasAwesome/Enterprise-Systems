package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String bankKey, String personKey, String accountType, int holdings);

    public Account find(long id);

    public List<Account> findAll();

    public long debit(long id, int amount);

    public long credit(long id, int amount);

    public TransactionEntityFacade getTransactionEntityFacade();
}
