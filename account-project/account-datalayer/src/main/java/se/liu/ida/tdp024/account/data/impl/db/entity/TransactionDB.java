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

    private String type;

    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private String status;

    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;

    // --- Getters and Setters --- //

    public long getID() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
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
}
