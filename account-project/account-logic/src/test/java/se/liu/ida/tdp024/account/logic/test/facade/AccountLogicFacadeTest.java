package se.liu.ida.tdp024.account.logic.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

public class AccountLogicFacadeTest {

  // --- MockObjects ---//

  // --- Unit under test ---//
  public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(new TransactionEntityFacadeDB()), new BankAPIRuby(), new PersonAPIRust());
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

    assertNotEquals(accountLogicFacade.create("CHECK", validPersonKey, validBankName), 0);
    assertNotEquals(accountLogicFacade.create("SAVINGS", validPersonKey, validBankName), 0);
    assertEquals(accountLogicFacade.create("CHECK", invalidPersonKey, validBankName), 0);
    assertEquals(accountLogicFacade.create("CHECK", validPersonKey, invalidBankName), 0);
    assertEquals(accountLogicFacade.create("CHECK", invalidPersonKey, invalidBankName), 0);
    assertEquals(accountLogicFacade.create("CREDITCARD", validPersonKey, validBankName), 0);

  }

  @Test
  public void testFind() {
    String invalidPersonKey = "invalid";
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";

    accountLogicFacade.create("CHECK", "ahRtu7874Gdgd345Tlgd39TyurrG7", "SWEDBANK");

    assertFalse(accountLogicFacade.find(validPersonKey).isEmpty());
    assertTrue(accountLogicFacade.find(invalidPersonKey).isEmpty());

  }

  @Test
  public void testDebit() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String validBankName = "SWEDBANK";

    long accountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
    assertNotEquals(0, accountLogicFacade.debit(accountID, 0));
    assertEquals(0, accountLogicFacade.debit(accountID, 10));
    assertEquals(0, accountLogicFacade.debit(0, 0));
  }

  @Test
  public void testCredit() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String validBankName = "SWEDBANK";

    long accountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
    assertNotEquals(0, accountLogicFacade.credit(accountID, 0));
    assertNotEquals(0, accountLogicFacade.credit(accountID, 10));
    assertEquals(0, accountLogicFacade.credit(0, 0));
  }

  @Test
  public void testListTransaction() {
    String validPersonKey = "ahRtu7874Gdgd345Tlgd39TyurrG7";
    String diffrentValidPersonKey = "2657TyuhR57575GGhu7";
    String validBankName = "SWEDBANK";

    long correctAccountID = accountLogicFacade.create("CHECK", validPersonKey, validBankName);
    long diversionAccountID = accountLogicFacade.create("CHECK", diffrentValidPersonKey, validBankName);

    accountLogicFacade.credit(diversionAccountID, 10);
    accountLogicFacade.credit(diversionAccountID, 10);
    accountLogicFacade.credit(correctAccountID, 10);
    accountLogicFacade.credit(correctAccountID, 10);
    accountLogicFacade.credit(correctAccountID, 10);
    accountLogicFacade.credit(correctAccountID, 10);

    assertEquals(4, accountLogicFacade.transactions(correctAccountID).size());
  }

}
