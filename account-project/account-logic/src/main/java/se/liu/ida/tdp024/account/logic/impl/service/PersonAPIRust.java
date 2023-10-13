package se.liu.ida.tdp024.account.logic.impl.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.utils.dto.PersonDTO;

public class PersonAPIRust implements PersonAPI{

    @Override
    public List<PersonDTO> listAll() {
        return null;
    }

    @Override
    public List<PersonDTO> findByName(String name) {
        return null;
    }

    @Override
    public PersonDTO findByKey(String key) {
        PersonDTO p = new PersonDTO();
        p.setKey(key);
        p.setName("kalle");
        return p;
    }
    
}
