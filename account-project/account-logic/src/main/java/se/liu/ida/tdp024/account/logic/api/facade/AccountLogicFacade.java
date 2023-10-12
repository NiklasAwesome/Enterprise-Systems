package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountLogicFacade {
    public String create(String accountType, String personKey, String bankName);

    public List<Account> find(String personKey);

    public String debit(String accountID, int amount);

    public String credit(String accountID, int amount);

    public List<Transaction> transactions(String accountID);
}
