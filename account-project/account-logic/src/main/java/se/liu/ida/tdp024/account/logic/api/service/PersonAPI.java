package se.liu.ida.tdp024.account.logic.api.service;

import java.util.List;
import se.liu.ida.tdp024.account.utils.dto.PersonDTO;

public interface PersonAPI {
    public List<PersonDTO> listAll();

    public List<PersonDTO> findByName(String name);

    public PersonDTO findByKey(String key);
}
