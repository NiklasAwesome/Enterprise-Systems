package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class TransactionDB implements Transaction {
    
    @Id
    @GeneratedValue
    private long id;

    private String transactionType;

    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String status;

    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;

    // --- Getters and Setters --- //

    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "{\n" 
        + "id: " + this.id + ",\n" 
        + "type: " + this.transactionType + ",\n" 
        + "amount: " + this.amount + ",\n" 
        + "TimeStamp: " + this.timestamp + ",\n" 
        + "Status: " + this.status + ",\n"
        + "Account: " + this.account + "\n" 
        + "}";
    }
}
