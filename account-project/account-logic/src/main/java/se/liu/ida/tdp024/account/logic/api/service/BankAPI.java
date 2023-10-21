package se.liu.ida.tdp024.account.logic.api.service;

import java.util.List;

import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;

public interface BankAPI {
    public List<BankDTO> listAll() throws Exception;

    public BankDTO findByName(String name) throws Exception;

    public BankDTO findByKey(String key) throws Exception;
}
