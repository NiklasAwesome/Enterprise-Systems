package se.liu.ida.tdp024.account.logic.api.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;

public interface PersonAPI {
    public List<PersonDTO> listAll() throws Exception;

    public List<PersonDTO> findByName(String name) throws Exception;

    public PersonDTO findByKey(String key) throws Exception;
}
