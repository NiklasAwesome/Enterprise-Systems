package se.liu.ida.tdp024.account.logic.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.service.BankAPIRuby;
import se.liu.ida.tdp024.account.logic.impl.service.PersonAPIRust;
import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;

public class AccountLogicFacadeTest {

  // --- MockObjects ---//

  // --- Unit under test ---//
  public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(
      new AccountEntityFacadeDB(new TransactionEntityFacadeDB()), new BankAPIRuby(), new PersonAPIRust());
  public StorageFacade storageFacade = new StorageFacadeDB();

  @After
  @Before
  public void tearDown() {
    if (storageFacade != null)
      storageFacade.emptyStorage();
  }

  // --- Tests --- //

  @Test
  public void testCreate() {
    String invalidPersonKey = "invalid";
    String invalidBankName = "FakeBank";
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String validBankName = "SWEDBANK";

    try {
      assertNotEquals(accountLogicFacade.create("CHECK", validPersonKey, validBankName), 0);
      assertNotEquals(accountLogicFacade.create("SAVINGS", validPersonKey, validBankName), 0);
      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.create("CHECK", invalidPersonKey, validBankName);
      });
      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.create("CHECK", validPersonKey, invalidBankName);
      });
      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.create("CHECK", invalidPersonKey, invalidBankName);
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.create("CREDITCARD", validPersonKey, validBankName);
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testFind() {
    String invalidPersonKey = "invalid";
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    try {
      accountLogicFacade.create("CHECK", "ahRtu7874Gdgd345Tlgd39TyurrG7", "SWEDBANK");

      assertFalse(accountLogicFacade.find(validPersonKey).isEmpty());

      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.find(invalidPersonKey);
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testDebit() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String validBankName = "SWEDBANK";
    try {
      
      long accountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
      assertNotEquals(0, accountLogicFacade.debit(accountID + "", "0"));
      assertThrows(AccountInsufficentHoldingsException.class, () -> {
        accountLogicFacade.debit(accountID + "", "10");
      });
      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.debit("0", "0");
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.debit("0", "tusen");
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.debit("Kalle", "0");
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCredit() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String validBankName = "SWEDBANK";
    try {
      
      long accountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
      assertNotEquals(0, accountLogicFacade.credit(accountID + "", "0"));
      assertNotEquals(0, accountLogicFacade.credit(accountID + "", "10"));
      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.credit("0", "0");
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.credit("Kalle", "0");
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.credit("0", "Tusen");
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testListTransaction() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String diffrentValidPersonKey = "2657TyuhR57575GGhu7";
    String validBankName = "SWEDBANK";
    try {
      
      long correctAccountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
      long diversionAccountID = accountLogicFacade.create("CHECK", diffrentValidPersonKey, validBankName);
      
      accountLogicFacade.credit(diversionAccountID + "", "10");
      accountLogicFacade.credit(diversionAccountID + "", "10");
      accountLogicFacade.credit(correctAccountID + "", "10");
      accountLogicFacade.credit(correctAccountID + "", "10");
      accountLogicFacade.credit(correctAccountID + "", "10");
      accountLogicFacade.credit(correctAccountID + "", "10");
      
      assertEquals(4, accountLogicFacade.transactions(correctAccountID + "").size());

      assertThrows(AccountEntityNotFoundException.class, () -> {
        accountLogicFacade.transactions("1000");
      });
      assertThrows(AccountInputParameterException.class, () -> {
        accountLogicFacade.transactions("Kalle");
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
