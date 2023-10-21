package se.liu.ida.tdp024.account.data.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public class AccountEntityFacadeTest {

    // ---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB(new TransactionEntityFacadeDB());
    private StorageFacade storageFacade = new StorageFacadeDB();

    // ---- Variables for test ----//
    static final String BANK_KEY = "3";
    static final String PERSON_KEY = "098734h90723";

    @After
    @Before
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateAndFind() {
        try {
            long id = accountEntityFacade.create(BANK_KEY, PERSON_KEY, "CHECK", 0);
            Account account = accountEntityFacade.find(id);
            assertEquals(account.getBankKey(), BANK_KEY);
            assertEquals(account.getPersonKey(), PERSON_KEY);
            assertEquals(account.getAccountType(), "CHECK");

        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }

    }

    @Test
    public void testCreateInvalidParameters() {
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create("", "1", "CHECK", 0);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create(null, "1", "CHECK", 0);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create("1", "", "CHECK", 0);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create("1", null, "CHECK", 0);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create("1", "1", "", 0);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            accountEntityFacade.create("1", "1", null, 0);
        });
    }

    @Test
    public void testFindException() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            accountEntityFacade.find(1000000);
        });
    }

    @Test
    public void testGetAll() {
        try {
            String[] bankArray = { "1", "2", "3", "4" };
            String[] personArray = { "p1", "p2", "p3", "p4" };
            List<Account> accountList = new ArrayList<Account>();

            for (int i = 0; i < 4; i++) {
                long id = accountEntityFacade.create(bankArray[i], personArray[i], "CHECK", 0);
                accountList.add(accountEntityFacade.find(id));
            }

            List<Account> findAllList = accountEntityFacade.findAll();

            assertEquals(4, findAllList.size());

            assertEquals(bankArray[0], findAllList.get(0).getBankKey());
            assertEquals(bankArray[1], findAllList.get(1).getBankKey());
            assertEquals(bankArray[2], findAllList.get(2).getBankKey());
            assertEquals(bankArray[3], findAllList.get(3).getBankKey());
            assertEquals(personArray[0], findAllList.get(0).getPersonKey());
            assertEquals(personArray[1], findAllList.get(1).getPersonKey());
            assertEquals(personArray[2], findAllList.get(2).getPersonKey());
            assertEquals(personArray[3], findAllList.get(3).getPersonKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testFindAllException() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            accountEntityFacade.findAll();
        });
    }

    @Test
    public void testFindByPerson() {
        try {
            String personKey = "123";
            String[] bankArray = { "1", "2", "3", "4" };
            List<Account> accountList = new ArrayList<Account>();

            for (int i = 0; i < 4; i++) {
                long id = accountEntityFacade.create(bankArray[i], personKey, "CHECK", 0);
                accountList.add(accountEntityFacade.find(id));
            }

            long deversionAccountID = accountEntityFacade.create("1", "1234", "CHECK", 0);
            Account deversionAccount;
            List<Account> findAllList;

            findAllList = accountEntityFacade.findByPerson(personKey);
            deversionAccount = accountEntityFacade.find(deversionAccountID);

            assertEquals(4, findAllList.size());

            assertEquals(bankArray[0], findAllList.get(0).getBankKey());
            assertEquals(bankArray[1], findAllList.get(1).getBankKey());
            assertEquals(bankArray[2], findAllList.get(2).getBankKey());
            assertEquals(bankArray[3], findAllList.get(3).getBankKey());
            assertEquals(personKey, findAllList.get(0).getPersonKey());
            assertEquals(personKey, findAllList.get(1).getPersonKey());
            assertEquals(personKey, findAllList.get(2).getPersonKey());
            assertEquals(personKey, findAllList.get(3).getPersonKey());
            assertNotEquals(deversionAccount.getPersonKey(), findAllList.get(0).getPersonKey());
            assertNotEquals(deversionAccount.getPersonKey(), findAllList.get(1).getPersonKey());
            assertNotEquals(deversionAccount.getPersonKey(), findAllList.get(2).getPersonKey());
            assertNotEquals(deversionAccount.getPersonKey(), findAllList.get(3).getPersonKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByPersonIfEmpty() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            accountEntityFacade.findByPerson("PersonWhoDoesntExist");

        });
    }

    @Test
    public void testDebit() {
        try {
            long id = accountEntityFacade.create(BANK_KEY, PERSON_KEY, "CHECK", 10);
            long transactionID;
            Transaction transaction;
            Account account;
            transactionID = accountEntityFacade.debit(id, 10);
            account = accountEntityFacade.find(id);
            transaction = accountEntityFacade.getTransactionEntityFacade().find(transactionID);

            assertEquals(transaction.getStatus(), "OK");
            assertEquals(transaction.getAmount(), 10);
            assertEquals(account.getHoldings(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDebitFail() {
        try {
            long id = accountEntityFacade.create(BANK_KEY, PERSON_KEY, "CHECK", 0);
            try {
                accountEntityFacade.debit(id, 10);
                
            } catch (AccountInsufficentHoldingsException e) {
                Transaction transaction = accountEntityFacade.getTransactionEntityFacade().findByPerson(id).get(0);
                
                assertEquals(transaction.getStatus(), "FAILED");
                assertEquals(transaction.getAmount(), 10);
                assertEquals(transaction.getAccount().getHoldings(), 0);
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDebitIfAccountDoesNotExist() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            accountEntityFacade.debit(100000000, 0);
        });

    }

    @Test
    public void testCredit() {
        try {
            long id = accountEntityFacade.create(BANK_KEY, PERSON_KEY, "CHECK", 0);
            long transactionID;
            Transaction transaction;
            Account account;
            transactionID = accountEntityFacade.credit(id, 10);
            account = accountEntityFacade.find(id);
            transaction = accountEntityFacade.getTransactionEntityFacade().find(transactionID);

            assertEquals(transaction.getStatus(), "OK");
            assertEquals(transaction.getAmount(), 10);
            assertEquals(account.getHoldings(), 10);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreditIfAccountDoesNotExist() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            accountEntityFacade.credit(100000000, 0);
        });
    }
}