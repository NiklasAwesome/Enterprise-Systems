package se.liu.ida.tdp024.account.rest;

import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;

public class AccountControllerTest {

    public AccountController ac = new AccountController();
    public StorageFacade storageFacade = new StorageFacadeDB();

    @After
    @Before
    public void tearDown() {
        if (storageFacade != null)
            storageFacade.emptyStorage();
    }

    @Test
    public void createTest() {
        String response = ac.create("CHECK", "1", "SWEDBANK");
        assertEquals("OK", response);

        String failedResponse = ac.create("Creditcard", "1", "SWEDBANK");
        assertEquals("FAILED", failedResponse);
    }

    @Test
    public void findTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        String response = ac.find("1");
        assertNotEquals("[]", response);

        String failedResponse = ac.find("100000000");
        assertEquals("[]", failedResponse);
    }

    @Test
    public void debitTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        String response = ac.debit("1", "0");
        assertEquals("OK", response);

        String failedResponse = ac.debit("10000", "100");
        assertEquals("FAILED", failedResponse);
    }

    @Test
    public void creditTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        String response = ac.credit("1", "0");
        assertEquals("OK", response);

        String failedResponse = ac.credit("10000", "100");
        assertEquals("FAILED", failedResponse);
    }

    @Test
    public void transactionsTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        ac.credit("1", "0");
        ac.credit("1", "0");
        ac.credit("1", "0");

        String response = ac.transactions("1");
        assertNotEquals("[]", response);

        String failedResponse = ac.transactions("100");
        assertEquals("[]", failedResponse);
    }
}
