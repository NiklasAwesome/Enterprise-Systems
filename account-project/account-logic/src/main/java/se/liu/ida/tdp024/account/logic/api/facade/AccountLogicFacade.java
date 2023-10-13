package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountLogicFacade {
    public long create(String accountType, String personKey, String bankName);

    public List<Account> find(String personKey);

    public long debit(long accountID, int amount);

    public long credit(long accountID, int amount);

    public List<Transaction> transactions(long accountID);
}
