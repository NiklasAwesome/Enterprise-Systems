package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;

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
    public long create(String accountType, String personKey, String bankName) throws Exception {
        try {

            BankDTO bank = bankAPI.findByName(bankName);
            PersonDTO person = personAPI.findByKey(personKey);
            if (bank == null) {
                throw new AccountEntityNotFoundException("bank");
            }
            if (person == null) {
                throw new AccountEntityNotFoundException("person");
            }
            if (accountType.equals("CHECK") || accountType.equals("SAVINGS")) {
                long accountID = accountEntityFacade.create(bank.getKey(), person.getKey(), accountType, 0);
                return accountID;

            } else {
                throw new AccountInputParameterException("accountType");
            }
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<Account> find(String personKey) throws Exception {
        return accountEntityFacade.findByPerson(personKey);
    }

    @Override
    public long debit(String accountID, String amount) throws Exception {
        try {
            long id = Long.parseLong(accountID);
            int am = Integer.parseInt(amount);
            return accountEntityFacade.debit(id, am);
        } catch (NumberFormatException e) {
            throw new AccountInputParameterException("parseError");
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public long credit(String accountID, String amount) throws Exception {
        try {
            long id = Long.parseLong(accountID);
            int am = Integer.parseInt(amount);
            return accountEntityFacade.credit(id, am);
        } catch (NumberFormatException e) {
            throw new AccountInputParameterException("parseError");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Transaction> transactions(String accountID) throws Exception {
        try {
            long id = Long.parseLong(accountID);
            return accountEntityFacade.getTransactionEntityFacade().findByPerson(id);
        } catch (NumberFormatException e) {
            throw new AccountInputParameterException("parseError");
        } catch (Exception e) {
            throw e;
        }
    }

}
