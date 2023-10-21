package se.liu.ida.tdp024.account.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.service.BankAPIRuby;
import se.liu.ida.tdp024.account.logic.impl.service.PersonAPIRust;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;
import se.liu.ida.tdp024.account.utils.api.logger.AccountLogger;
import se.liu.ida.tdp024.account.utils.impl.helpers.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.utils.impl.logger.AccountLoggerKafka;

@RestController
@RequestMapping("account-rest/account")
public class AccountController {
    private AccountLogicFacade alf = new AccountLogicFacadeImpl(
            new AccountEntityFacadeDB(new TransactionEntityFacadeDB()), new BankAPIRuby(), new PersonAPIRust());
    private AccountLogger al = new AccountLoggerKafka("rest");
    private AccountJsonSerializer ajs = new AccountJsonSerializerImpl();

    @RequestMapping(path = "/create", produces = "text/html")
    public ResponseEntity<String> create(@RequestParam(value = "accounttype", defaultValue = "null") String AccountType,
            @RequestParam(value = "person", defaultValue = "null") String person,
            @RequestParam(value = "bank", defaultValue = "null") String bank) {
        al.log("REST: " + String.format("/account-rest/account/create?accounttype=%1$s&person=%2$s&bank=%3$s", AccountType, person, bank));
        try {
            long id = alf.create(AccountType, person, bank);
            return new ResponseEntity<String>("Account created OK with ID: " + id, HttpStatus.CREATED);
            
        } catch (AccountEntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountInputParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/find/person", produces = "application/json")
    public ResponseEntity<String> find(@RequestParam(value = "person") String key) {
        al.log("REST: " + String.format("/account-rest/account/find/person?person=%s", key));
        try {
            String response = ajs.toJson(alf.find(key));
            return new ResponseEntity<String>(response, HttpStatus.OK);
        } catch (AccountEntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountInputParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/debit", produces = "text/html")
    public ResponseEntity<String> debit(@RequestParam(value = "id") String id, @RequestParam(value = "amount") String amount) {
        al.log("REST: " + String.format("/account-rest/account/debit?id=%1$s&amount=%2$s", id, amount));
        try {
            long tid = alf.debit(id, amount);
            return new ResponseEntity<String>(String.format("Account %s debited by amount %s and that transaction has the id %s", id, amount, tid), HttpStatus.OK);
        } catch (AccountInsufficentHoldingsException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountEntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountInputParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/credit", produces = "text/html")
    public ResponseEntity<String> credit(@RequestParam(value = "id") String id, @RequestParam(value = "amount") String amount) {
        al.log("REST: " + String.format("/account-rest/account/credit?id=%1$s&amount=%2$s", id, amount));
        try {
            long tid = alf.credit(id, amount);
            return new ResponseEntity<String>(String.format("Account %s credited by amount %s and that transaction has the id %s", id, amount, tid), HttpStatus.OK);
        } catch (AccountEntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountInputParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/transactions", produces = "application/json")
    public ResponseEntity<String> transactions(@RequestParam(value = "id") String id) {
        al.log("REST: " + "/account-rest/account/transactions");
        try {
            String response = ajs.toJson(alf.transactions(id));
            return new ResponseEntity<String>(response, HttpStatus.OK);
        } catch (AccountEntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountInputParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
