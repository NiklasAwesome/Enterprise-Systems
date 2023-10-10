package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {

    @Id
    @GeneratedValue
    private long id;

    private String personKey;

    private String accountType;

    private String bankKey;

    private int holdings;

    // --- Setters and Getters ---//

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    @Override
    public String getPersonKey() {
        return this.personKey;
    }

    @Override
    public void setPersonKey(String personKey) {
        this.personKey = personKey;
    }

    @Override
    public String getAccountType() {
        return this.accountType;
    }

    @Override
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String getBankKey() {
        return this.bankKey;
    }

    @Override
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    @Override
    public int getHoldings() {
        return this.holdings;
    }

    @Override
    public void setHoldnings(int holdings) {
        this.holdings = holdings;
    }

    @Override
    public String toString() {
        return "{\n" 
        + "id: " + this.id + ",\n" 
        + "personKey: " + this.personKey + ",\n" 
        + "accountType: " + this.accountType + ",\n" 
        + "bankKey: " + this.bankKey + ",\n" 
        + "holdings: " + this.holdings + "\n" 
        + "}";
    }

}
