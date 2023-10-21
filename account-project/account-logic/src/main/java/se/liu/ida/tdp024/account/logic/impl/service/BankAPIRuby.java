package se.liu.ida.tdp024.account.logic.impl.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;
import se.liu.ida.tdp024.account.utils.api.helpers.HTTPHelper;
import se.liu.ida.tdp024.account.utils.impl.helpers.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.utils.impl.helpers.HTTPHelperImpl;

public class BankAPIRuby implements BankAPI {
    private HTTPHelper httphelper = new HTTPHelperImpl();
    private AccountJsonSerializer ajs = new AccountJsonSerializerImpl();
    String bankAPIAdress = "http://localhost:8070/bank/";
    

    @Override
    public List<BankDTO> listAll() throws Exception {
        String resultJson = httphelper.get(bankAPIAdress + "list");
        return ajs.fromJsonList(resultJson, BankDTO.class);
    }

    @Override
    public BankDTO findByName(String name) throws Exception {
        String restultJson = httphelper.get(bankAPIAdress + "find.name", "name", name);
        return ajs.fromJson(restultJson, BankDTO.class);
    }

    @Override
    public BankDTO findByKey(String key) throws Exception {
        String restultJson = httphelper.get(bankAPIAdress + "find.key", "key", key);
        return ajs.fromJson(restultJson, BankDTO.class);
    }

}
