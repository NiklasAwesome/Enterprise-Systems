package se.liu.ida.tdp024.account.logic.impl.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;
import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;
import se.liu.ida.tdp024.account.utils.api.helpers.HTTPHelper;
import se.liu.ida.tdp024.account.utils.impl.helpers.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.utils.impl.helpers.HTTPHelperImpl;

public class PersonAPIRust implements PersonAPI{
    private HTTPHelper httphelper = new HTTPHelperImpl();
    private AccountJsonSerializer ajs = new AccountJsonSerializerImpl();
    String bankAPIAdress = "http://localhost:8060/person/";

    @Override
    public List<PersonDTO> listAll() {
        String resultJson = httphelper.get(bankAPIAdress + "list");
        return ajs.fromJsonList(resultJson, PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findByName(String name) {
        String restultJson = httphelper.get(bankAPIAdress + "find.name", "name", name);
        return ajs.fromJsonList(restultJson, PersonDTO.class);
    }

    @Override
    public PersonDTO findByKey(String key) {
        String restultJson = httphelper.get(bankAPIAdress + "find.key", "key", key);
        return ajs.fromJson(restultJson, PersonDTO.class);
    }
    
}
