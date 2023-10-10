package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String bankKey, String personKey, String accountType);

    public Account find(long id);

    public List<Account> findAll();
}
