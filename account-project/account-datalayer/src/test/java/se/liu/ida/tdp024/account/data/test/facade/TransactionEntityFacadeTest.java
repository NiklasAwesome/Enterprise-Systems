package se.liu.ida.tdp024.account.data.test.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class TransactionEntityFacadeTest {

    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB(transactionEntityFacade);
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateAndFind() {
        try {
            long accountID = accountEntityFacade.create("1", "1", "CHECK", 10);

            long transactionID = accountEntityFacade.debit(accountID, 2);
            Account account = accountEntityFacade.find(accountID);

            Transaction transaction = transactionEntityFacade.find(transactionID);

            assertEquals(transaction.getType(), "DEBIT");
            assertEquals(transaction.getAmount(), 2);
            assertEquals(transaction.getAccount().getID(), accountID);
            assertEquals(transaction.getStatus(), "OK");
            assertNotEquals(transaction.getCreated(), null);
            assertEquals(account.getHoldings(), 8);

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testCreateAndFindIfFailed() {
        try {

            long accountID = accountEntityFacade.create("1", "1", "CHECK", 0);
            try {
                accountEntityFacade.debit(accountID, 2);
            } catch (Exception e) {
                Transaction transaction = transactionEntityFacade.findByPerson(accountID).get(0);
                assertEquals(transaction.getType(), "DEBIT");
                assertEquals(transaction.getAmount(), 2);
                assertEquals(transaction.getAccount().getID(), accountID);
                assertEquals(transaction.getStatus(), "FAILED");
                assertEquals(transaction.getAccount().getHoldings(), 0);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidParameters() {
        assertThrows(AccountInputParameterException.class, () -> {
            transactionEntityFacade.create(null, 0, null, false, null);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            transactionEntityFacade.create("DEBIT", 0, new AccountDB(), false, null);
        });
        assertThrows(AccountInputParameterException.class, () -> {
            transactionEntityFacade.create("", 0, new AccountDB(), false, EMF.getEntityManager());
        });
        assertThrows(AccountInputParameterException.class, () -> {
            transactionEntityFacade.create("BEDIT", 0, null, false, EMF.getEntityManager());
        });
        assertThrows(AccountInputParameterException.class, () -> {
            transactionEntityFacade.create(null, 0, null, false, EMF.getEntityManager());
        });
    }

    @Test
    public void testFind() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            transactionEntityFacade.find(1000000);
        });
    }

    @Test
    public void testFindByPerson() {
        try {

            long accountID = accountEntityFacade.create("1", "1", "CHECK", 1000);

            int[] amountArray = { 100, 50, 25, 10 };
            List<Transaction> transactionList = new ArrayList<Transaction>();

            for (int i = 0; i < 4; i++) {
                long id = accountEntityFacade.debit(accountID, amountArray[i]);
                Transaction transactionToAdd = transactionEntityFacade.find(id);
                transactionList.add(transactionToAdd);
            }

            long diversionAccountID = accountEntityFacade.create("1", "2", "CHECK", 0);
            long diversionTransactionID = accountEntityFacade.credit(diversionAccountID, 4);
            Transaction diversionTransaction = transactionEntityFacade.find(diversionTransactionID);

            List<Transaction> transactionFindByPersonList = transactionEntityFacade.findByPerson(accountID);

            assertEquals(4, transactionFindByPersonList.size());

            assertNotEquals(diversionTransaction.getAccount().getID(),
                    transactionFindByPersonList.get(0).getAccount().getID());
            assertNotEquals(diversionTransaction.getAccount().getID(),
                    transactionFindByPersonList.get(1).getAccount().getID());
            assertNotEquals(diversionTransaction.getAccount().getID(),
                    transactionFindByPersonList.get(2).getAccount().getID());
            assertNotEquals(diversionTransaction.getAccount().getID(),
                    transactionFindByPersonList.get(3).getAccount().getID());

            assertEquals(amountArray[0], transactionFindByPersonList.get(0).getAmount());
            assertEquals(amountArray[1], transactionFindByPersonList.get(1).getAmount());
            assertEquals(amountArray[2], transactionFindByPersonList.get(2).getAmount());
            assertEquals(amountArray[3], transactionFindByPersonList.get(3).getAmount());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByPersonIfEmpty() {
        try {
            long accountID = accountEntityFacade.create("1", "1", "CHECK", 0);
            assertThrows(AccountEntityNotFoundException.class, () -> {
                transactionEntityFacade.findByPerson(accountID);
            });

            assertThrows(AccountEntityNotFoundException.class, () -> {
                transactionEntityFacade.findByPerson(10000000);
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAll() {
        try {

            long accountID = accountEntityFacade.create("1", "1", "CHECK", 0);
            long accountID2 = accountEntityFacade.create("1", "4", "CHECK", 0);

            long[] accountIDArray = { accountID, accountID2, accountID, accountID2 };
            int[] amountArray = { 100, 50, 25, 10 };

            List<Transaction> transactionList = new ArrayList<Transaction>();

            for (int i = 0; i < 4; i++) {
                long id = accountEntityFacade.credit(accountIDArray[i], amountArray[i]);
                Transaction transactionToAdd = transactionEntityFacade.find(id);
                transactionList.add(transactionToAdd);
            }
            List<Transaction> transactionFindAllList = transactionEntityFacade.findAll();

            assertEquals(4, transactionFindAllList.size());

            assertEquals(accountIDArray[0], transactionFindAllList.get(0).getAccount().getID());
            assertEquals(accountIDArray[1], transactionFindAllList.get(1).getAccount().getID());
            assertEquals(accountIDArray[2], transactionFindAllList.get(2).getAccount().getID());
            assertEquals(accountIDArray[3], transactionFindAllList.get(3).getAccount().getID());

            assertEquals(amountArray[0], transactionFindAllList.get(0).getAmount());
            assertEquals(amountArray[1], transactionFindAllList.get(1).getAmount());
            assertEquals(amountArray[2], transactionFindAllList.get(2).getAmount());
            assertEquals(amountArray[3], transactionFindAllList.get(3).getAmount());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllIfEmpty() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            transactionEntityFacade.findAll();
        });
    }

}
