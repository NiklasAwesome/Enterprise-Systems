package se.liu.ida.tdp024.account.rest;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

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
        {
            ResponseEntity<String> re = ac.create("CHECK", "1", "SWEDBANK");
            assertEquals(201, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.create("null", "1", "SWEDBANK");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.create("CHECK", "1020938092", "SWEDBANK");
            assertEquals(404, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.create("CHECK", "1", "BigBank");
            assertEquals(404, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.create("CHECK", "kalle", "SWEDBANK");
            assertEquals(404, re.getStatusCodeValue());
        }
    }

    @Test
    public void findTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        {
            ResponseEntity<String> re = ac.find("1");
            assertNotEquals("[]", re.getBody());
            assertEquals(200, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.find("kalle");
            assertNotEquals("[]", re.getBody());
            assertEquals(404, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.find("10");
            assertNotEquals("[]", re.getBody());
            assertEquals(404, re.getStatusCodeValue());
        }

    }

    @Test
    public void debitTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        {
            ResponseEntity<String> re = ac.debit("1", "0");
            assertEquals(200, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.debit("1", "10");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.debit("1", "Tio");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.debit("kalle", "10");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.debit("2", "0");
            assertEquals(404, re.getStatusCodeValue());
        }
    }

    @Test
    public void creditTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        {
            ResponseEntity<String> re = ac.credit("1", "0");
            assertEquals(200, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.credit("1", "Tio");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.credit("kalle", "10");
            assertEquals(400, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.credit("2", "0");
            assertEquals(404, re.getStatusCodeValue());
        }
    }

    @Test
    public void transactionsTest() {
        ac.create("CHECK", "1", "SWEDBANK");
        ac.create("SAVINGS", "1", "SWEDBANK");
        ac.credit("1", "0");
        ac.credit("1", "0");
        ac.credit("1", "0");

        {
            ResponseEntity<String> re = ac.transactions("1");
            assertEquals(200, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.transactions("10");
            assertEquals(404, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.transactions("2");
            assertEquals(404, re.getStatusCodeValue());
        }
        {
            ResponseEntity<String> re = ac.transactions("Kalle");
            assertEquals(400, re.getStatusCodeValue());
        }
    }
}
