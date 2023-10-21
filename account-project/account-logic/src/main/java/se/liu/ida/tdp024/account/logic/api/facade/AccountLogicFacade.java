package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountLogicFacade {
    public long create(String accountType, String personKey, String bankName) throws Exception;

    public List<Account> find(String personKey) throws Exception;

    public long debit(String accountID, String amount) throws Exception;

    public long credit(String accountID, String amount) throws Exception;

    public List<Transaction> transactions(String accountID) throws Exception;
}
