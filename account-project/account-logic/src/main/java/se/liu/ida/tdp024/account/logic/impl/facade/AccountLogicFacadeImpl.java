package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.utils.dto.BankDTO;
import se.liu.ida.tdp024.account.utils.dto.PersonDTO;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private BankAPI bankAPI;
    private PersonAPI personAPI;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, BankAPI bankAPI, PersonAPI personAPI) {
        this.accountEntityFacade = accountEntityFacade;
        this.bankAPI = bankAPI;
        this.personAPI = personAPI;
    }

    @Override
    public long create(String accountType, String personKey, String bankName) {
        BankDTO bank = bankAPI.findByName(bankName);
        PersonDTO person = personAPI.findByKey(personKey);
        if (bank == null || person == null) {
            return 0;
        }

        long accountID = accountEntityFacade.create(bank.getKey(), person.getKey(), accountType, 0);

        return accountID;
    }

    @Override
    public List<Account> find(String personKey) {
        return accountEntityFacade.findByPerson(personKey);
    }

    @Override
    public long debit(long accountID, int amount) {
        long transactionID = accountEntityFacade.debit(accountID, amount);
        Transaction transaction = accountEntityFacade.getTransactionEntityFacade().find(transactionID);
        if(transaction != null && transaction.getStatus() == "OK") {
            return transactionID;
        } else {
            return 0;
        }
    }

    @Override
    public long credit(long accountID, int amount) {
        long transactionID = accountEntityFacade.credit(accountID, amount);
        Transaction transaction = accountEntityFacade.getTransactionEntityFacade().find(transactionID);
        if(transaction != null && transaction.getStatus() == "OK") {
            return transactionID;
        } else {
            return 0;
        }
    }

    @Override
    public List<Transaction> transactions(long accountID) {
        return accountEntityFacade.getTransactionEntityFacade().findByPerson(accountID);
    }
    
}
