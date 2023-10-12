package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade;
    private BankAPI bankAPI;
    private PersonAPI personAPI;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, TransactionEntityFacade transactionEntityFacade, BankAPI bankAPI, PersonAPI personAPI) {
        this.accountEntityFacade = accountEntityFacade;
        this.transactionEntityFacade = transactionEntityFacade;
        this.bankAPI = bankAPI;
        this.personAPI = personAPI;
    }

    @Override
    public String create(String accountType, String personKey, String bankName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<Account> find(String personKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public String debit(String accountID, int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'debit'");
    }

    @Override
    public String credit(String accountID, int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'credit'");
    }

    @Override
    public List<Transaction> transactions(String accountID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transactions'");
    }
    
}
