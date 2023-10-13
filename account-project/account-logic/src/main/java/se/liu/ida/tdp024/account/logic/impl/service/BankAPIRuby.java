package se.liu.ida.tdp024.account.logic.impl.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.utils.dto.BankDTO;

public class BankAPIRuby implements BankAPI {

    @Override
    public List<BankDTO> listAll() {
        return null;
    }

    @Override
    public BankDTO findByName(String name) {
        BankDTO b = new BankDTO();
        b.setKey("1");
        b.setName(name);
        return b;
    }

    @Override
    public BankDTO findByKey(String key) {
        return null;
    }

}
