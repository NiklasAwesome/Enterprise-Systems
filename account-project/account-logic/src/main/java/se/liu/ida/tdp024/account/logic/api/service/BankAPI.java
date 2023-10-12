package se.liu.ida.tdp024.account.logic.api.service;

import java.util.Map;

public interface BankAPI {
    public Map<String, String> listAll();

    public Map<String, String> findByName(String name);

    public String findByKey(String key);
}
