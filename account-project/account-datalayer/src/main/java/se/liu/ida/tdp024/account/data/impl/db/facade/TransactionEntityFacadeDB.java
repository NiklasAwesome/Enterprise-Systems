package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;

public class TransactionEntityFacadeDB implements TransactionEntityFacade{

    @Override
    public long create(String transactionType, int amount, long accountID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Transaction find(long transactionID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public ArrayList<Transaction> findByPerson(long accountID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPerson'");
    }

    @Override
    public ArrayList<Transaction> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
