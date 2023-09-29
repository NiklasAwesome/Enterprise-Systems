package se.liu.ida.tdp024.account.data.api.facade;

import java.util.ArrayList;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String bankKey, String personKey, String accountType);

    public Account find(long id);

    public ArrayList<Account> findAll();
}
