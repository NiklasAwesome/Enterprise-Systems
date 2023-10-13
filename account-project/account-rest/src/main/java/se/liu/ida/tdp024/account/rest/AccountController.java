package se.liu.ida.tdp024.account.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.service.BankAPIRuby;
import se.liu.ida.tdp024.account.logic.impl.service.PersonAPIRust;

@RestController
@RequestMapping("account-rest/account")
public class AccountController {
    private AccountLogicFacade alf = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(new TransactionEntityFacadeDB()), new BankAPIRuby(), new PersonAPIRust());

    @RequestMapping(path="/create", produces="text/html")
    public String create(@RequestParam(value="accounttype") String AccountType, @RequestParam(value="person") String person, @RequestParam(value="bank") String bank) {
        long id = alf.create(AccountType, person, bank);
        return id != 0 ? "OK": "FAILED";
    }

    @RequestMapping(path="/find/person", produces="application/json")
    public String find(@RequestParam(value="person", defaultValue = "1") String key){
        List<Account> acc = alf.find(key);
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(acc);
        } catch (Exception e) {
            return "FAIL BOIIIII";
        }
    }

    @RequestMapping(path="/debit", produces = "text/html")
    public String debit(@RequestParam(value="id") String id, @RequestParam(value="amount") String amount) {
        long tid = alf.debit(Long.parseLong(id), Integer.parseInt(amount));
        return tid != 0 ? "OK" : "FAILED";
    }

    @RequestMapping(path="/credit", produces = "text/html")
    public String credit(@RequestParam(value="id") String id, @RequestParam(value="amount") String amount) {
        long tid = alf.debit(Long.parseLong(id), Integer.parseInt(amount));
        return tid != 0 ? "OK" : "FAILED";
    }

    @RequestMapping(path = "/transactions", produces = "application/json")
    public String transactions(@RequestParam(value="id") String id) {
        List<Transaction> t = alf.transactions(Long.parseLong(id));
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(t);
        } catch (Exception e) {
            return "fail....";
        }
    }

}
