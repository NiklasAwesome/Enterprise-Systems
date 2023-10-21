package se.liu.ida.tdp024.account.logic.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;
import se.liu.ida.tdp024.account.logic.impl.service.PersonAPIRust;

public class PersonAPITest {
    private PersonAPI personapi = new PersonAPIRust();

    @Test
    public void bankDTOTest() {
        PersonDTO persondto = new PersonDTO();
        persondto.setKey("1");
        persondto.setName("kalle");

        assertEquals("1", persondto.getKey());
        assertEquals("kalle", persondto.getName());
    }

    @Test
    public void listTest() {
        try {
            List<PersonDTO> banklist = personapi.listAll();

            assertFalse(banklist.isEmpty());
            assertEquals("1", banklist.get(0).getKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findNameTest() {
        try {
            List<PersonDTO> personlist = personapi.listAll();
            for (PersonDTO person : personlist) {
                List<PersonDTO> personListByName = personapi.findByName(person.getName());
                Boolean oneMatchesKey = false;
                for (PersonDTO personByName : personListByName) {
                    assertEquals(person.getName(), personByName.getName());
                    if (person.getKey().equals(personByName.getKey())) {
                        oneMatchesKey = true;
                    }
                }
                assertTrue(oneMatchesKey);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findKeyTest() {
        try {
            List<PersonDTO> personlist = personapi.listAll();
            for (PersonDTO person : personlist) {
                PersonDTO personByKey = personapi.findByKey(person.getKey());
                assertEquals(person.getKey(), personByKey.getKey());
                assertEquals(person.getName(), personByKey.getName());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
