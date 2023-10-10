package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {
    public long create(String transactionType, int amount, long accountID);

    public Transaction find(long transactionID);

    public List<Transaction> findByPerson(long accountID);

    public List<Transaction> findAll();
}
