package se.liu.ida.tdp024.account.data.test.facade;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

public class TransactionEntityFacadeTest {

    private TransactionEntityFacade transactionEntityFacade;
    private AccountEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateAndFind() {
        long accountID = accountEntityFacade.create("1", "1", "CHECK");

        long transactionID = transactionEntityFacade.create("CREDIT", 2, accountID);

        Transaction transaction = transactionEntityFacade.find(transactionID);

        assertEquals(transaction.getTransactionType(), "CREDIT");
        assertEquals(transaction.getAmount(), 2);
        assertEquals(transaction.getAccount().getID(), accountID);

    }

    @Test
    public void testFindByPerson() {
        long accountID = accountEntityFacade.create("1", "1", "CHECK");

        String[] typeArray = { "CREDIT", "CREDIT", "CREDIT", "DEBIT" };
        int[] amountArray = { 100, 50, 25, 10 };
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

        for (int i = 0; i < 4; i++) {
            long id = transactionEntityFacade.create(typeArray[i], amountArray[i], accountID);
            transactionList.add(transactionEntityFacade.find(id));
        }

        long divertionAccountID = accountEntityFacade.create("1", "2", "CHECK");
        long divertionTransactionID = transactionEntityFacade.create("CREDIT", 1, divertionAccountID);
        Transaction divertionTransaction = transactionEntityFacade.find(divertionTransactionID);

        ArrayList<Transaction> transactionFindByPersonList = transactionEntityFacade.findByPerson(accountID);

        assertTrue(transactionFindByPersonList.containsAll(transactionList)
                && transactionList.containsAll(transactionFindByPersonList));
        assertFalse(transactionFindByPersonList.contains(divertionTransaction));

    }

    @Test
    public void testFindAll() {
        long accountID = accountEntityFacade.create("1", "1", "CHECK");
        long accountID2 = accountEntityFacade.create("1", "4", "CHECK");

        long[] accountIDArray = { accountID, accountID2, accountID, accountID2 };
        String[] typeArray = { "CREDIT", "CREDIT", "CREDIT", "DEBIT" };
        int[] amountArray = { 100, 50, 25, 10 };

        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

        for (int i = 0; i < 4; i++) {
            long id = transactionEntityFacade.create(typeArray[i], amountArray[i], accountIDArray[i]);
            transactionList.add(transactionEntityFacade.find(id));
        }
        ArrayList<Transaction> transactionFindAllList = transactionEntityFacade.findAll();

        assertTrue(transactionFindAllList.containsAll(transactionList)
                && transactionList.containsAll(transactionFindAllList));

    }

}
