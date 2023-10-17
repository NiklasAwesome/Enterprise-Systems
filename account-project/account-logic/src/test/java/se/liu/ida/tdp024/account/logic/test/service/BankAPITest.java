package se.liu.ida.tdp024.account.logic.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.logic.impl.service.BankAPIRuby;

public class BankAPITest {
    private BankAPI bankapi = new BankAPIRuby();

    @Test
    public void listTest() {
        List<BankDTO> banklist = bankapi.listAll();

        assertFalse(banklist.isEmpty());
        assertEquals("1", banklist.get(0).getKey());
    }

    @Test
    public void findNameTest() {
        List<BankDTO> banklist = bankapi.listAll();
        for (BankDTO bank : banklist) {
            BankDTO bankByName = bankapi.findByName(bank.getName());
            assertEquals(bank.getKey(), bankByName.getKey());
            assertEquals(bank.getName(), bankByName.getName());
        }
    }

    @Test
    public void findKeyTest() {
        List<BankDTO> banklist = bankapi.listAll();
        for (BankDTO bank : banklist) {
            BankDTO bankByName = bankapi.findByKey(bank.getKey());
            assertEquals(bank.getKey(), bankByName.getKey());
            assertEquals(bank.getName(), bankByName.getName());
        }
    }
}
