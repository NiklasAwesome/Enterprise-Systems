package se.liu.ida.tdp024.account.data.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public class AccountEntityFacadeTest {

    // ---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();

    // ---- Variables for test ----//
    static final String BANK_KEY = "3";
    static final String PERSON_KEY = "098734h90723";

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateAndFind() {
        long id = accountEntityFacade.create(BANK_KEY, PERSON_KEY, "CHECK");
        Account account = accountEntityFacade.find(id);
        assertEquals(account.getBankKey(), BANK_KEY);
        assertEquals(account.getPersonKey(), PERSON_KEY);

    }

    @Test
    public void testGetAll() {
        String[] bankArray = { "1", "2", "3", "4" };
        String[] personArray = { "p1", "p2", "p3", "p4" };
        ArrayList<Account> accountList = new ArrayList<Account>();

        for (int i = 0; i < 4; i++) {
            long id = accountEntityFacade.create(bankArray[i], personArray[i], "CHECK");
            accountList.add(accountEntityFacade.find(id));
        }

        ArrayList<Account> findAllList = accountEntityFacade.findAll();

        assertTrue(findAllList.containsAll(accountList) && accountList.containsAll(findAllList));

    }
}