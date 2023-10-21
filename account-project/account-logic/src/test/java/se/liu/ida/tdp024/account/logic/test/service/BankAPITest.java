package se.liu.ida.tdp024.account.logic.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.logic.impl.service.BankAPIRuby;

public class BankAPITest {
    private BankAPI bankapi = new BankAPIRuby();

    @Test
    public void bankDTOTest() {
        BankDTO bankdto = new BankDTO();
        bankdto.setKey("1");
        bankdto.setName("superbanken");

        assertEquals("1", bankdto.getKey());
        assertEquals("superbanken", bankdto.getName());
    }

    @Test
    public void listTest() {
        List<BankDTO> banklist;
        try {
            banklist = bankapi.listAll();
            assertFalse(banklist.isEmpty());
            assertEquals("1", banklist.get(0).getKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void findNameTest() {
        try {
            List<BankDTO> banklist = bankapi.listAll();
            for (BankDTO bank : banklist) {
                BankDTO bankByName = bankapi.findByName(bank.getName());
                assertEquals(bank.getKey(), bankByName.getKey());
                assertEquals(bank.getName(), bankByName.getName());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findKeyTest() {
        try {
            List<BankDTO> banklist = bankapi.listAll();
            for (BankDTO bank : banklist) {
                BankDTO bankByName = bankapi.findByKey(bank.getKey());
                assertEquals(bank.getKey(), bankByName.getKey());
                assertEquals(bank.getName(), bankByName.getName());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
