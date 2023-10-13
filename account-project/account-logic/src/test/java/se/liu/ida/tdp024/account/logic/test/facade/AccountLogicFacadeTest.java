package se.liu.ida.tdp024.account.logic.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.service.BankAPI;
import se.liu.ida.tdp024.account.logic.api.service.PersonAPI;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.utils.dto.BankDTO;
import se.liu.ida.tdp024.account.utils.dto.PersonDTO;

public class AccountLogicFacadeTest {

  // --- MockObjects ---//
  private BankAPI bankAPI = new BankAPI() {
    @Override
    public List<BankDTO> listAll() {
      return null;
    }

    @Override
    public BankDTO findByName(String name) {
      if (name == "SWEDBANK") {
        return new BankDTO();
      }
      return null;
    }

    @Override
    public BankDTO findByKey(String key) {
      return null;
    }
  };

  private PersonAPI personAPI = new PersonAPI() {
    @Override
    public List<PersonDTO> listAll() {
      return null;
    }

    @Override
    public List<PersonDTO> findByName(String name) {
      return null;
    }

    @Override
    public PersonDTO findByKey(String key) {
      return key == "invalid" ? null : new PersonDTO();
    }
  };

  public AccountEntityFacade aef = new AccountEntityFacade() {

    private Account account = new Account() {

      @Override
      public long getID() {
        return 1;
      }

      @Override
      public void setID(long id) {

      }

      @Override
      public String getPersonKey() {
        return "valid";
      }

      @Override
      public void setPersonKey(String personKey) {

      }

      @Override
      public String getAccountType() {
        return "";
      }

      @Override
      public void setAccountType(String accountType) {

      }

      @Override
      public String getBankKey() {
        return "valid";
      }

      @Override
      public void setBankKey(String bankKey) {

      }

      @Override
      public int getHoldings() {
        return 0;
      }

      @Override
      public void setHoldnings(int holdings) {

      }
    };

    @Override
    public long create(String bankKey, String personKey, String accountType, int holdings) {
      return 1;
    }

    @Override
    public Account find(long id) {
      if (id == 1) {
        return this.account;
      }
      return null;
    }

    @Override
    public List<Account> findByPerson(String personKey) {
      List<Account> returnList = new ArrayList<Account>();
      if (personKey == "invalid") {
        return returnList;
      }
      returnList.add(null);
      return returnList;
    }

    @Override
    public List<Account> findAll() {
      return null;
    }

    @Override
    public long debit(long id, int amount) {
      if (id == 0) {
        return 0;
      }
      if (amount != 0) {
        return 2;
      }
      return 1;

    }

    @Override
    public long credit(long id, int amount) {
      return id == 1 ? 1 : 0;
    }

    @Override
    public TransactionEntityFacade getTransactionEntityFacade() {
      return new TransactionEntityFacade() {

        private Transaction transaction = new Transaction() {
          private String status;

          @Override
          public long getID() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getID'");
          }

          @Override
          public void setID(long id) {

          }

          @Override
          public String getTransactionType() {
            return "";
          }

          @Override
          public void setTransactionType(String transactionType) {
          }

          @Override
          public int getAmount() {
            return 0;
          }

          @Override
          public void setAmount(int amount) {
          }

          @Override
          public Date getTimestamp() {
            return null;
          }

          @Override
          public void setTimestamp(Date timestamp) {
          }

          @Override
          public String getStatus() {
            return this.status;
          }

          @Override
          public void setStatus(String status) {
            this.status = status;
          }

          @Override
          public Account getAccount() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAccount'");
          }

          @Override
          public void setAccount(Account account) {
          }

        };

        @Override
        public long create(String transactionType, int amount, Account account, boolean success, EntityManager em) {
          return 0;
        }

        @Override
        public Transaction find(long transactionID) {
          if (transactionID == 2) {
            transaction.setStatus("FAILED");
            return transaction;
          }
          if (transactionID == 1) {
            transaction.setStatus("OK");
            return transaction;
          }
          return null;
        }

        @Override
        public List<Transaction> findByPerson(long accountID) {
          return new ArrayList<>(List.of(transaction, transaction, transaction, transaction));
        }

        @Override
        public List<Transaction> findAll() {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        }

      };
    }

  };
  // --- Unit under test ---//
  public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(aef, bankAPI, personAPI);
  public StorageFacade storageFacade;

  @After
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
    assertEquals(accountLogicFacade.create("CHECK", invalidPersonKey, validBankName), 0);
    assertEquals(accountLogicFacade.create("CHECK", validPersonKey, invalidBankName), 0);
    assertEquals(accountLogicFacade.create("CHECK", invalidPersonKey, invalidBankName), 0);

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
