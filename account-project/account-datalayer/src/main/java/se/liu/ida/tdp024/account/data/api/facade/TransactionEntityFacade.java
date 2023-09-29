package se.liu.ida.tdp024.account.data.api.facade;

import java.util.ArrayList;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {
    public long create(String transactionType, int amount, long accountID);

    public Transaction find(long transactionID);

    public ArrayList<Transaction> findByPerson(long accountID);

    public ArrayList<Transaction> findAll();
}
