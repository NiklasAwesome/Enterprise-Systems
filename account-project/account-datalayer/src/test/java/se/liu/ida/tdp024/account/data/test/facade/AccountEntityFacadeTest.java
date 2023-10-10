package se.liu.ida.tdp024.account.data.test.facade;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

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
        List<Account> accountList = new ArrayList<Account>();

        for (int i = 0; i < 4; i++) {
            long id = accountEntityFacade.create(bankArray[i], personArray[i], "CHECK");
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
       


    }
}