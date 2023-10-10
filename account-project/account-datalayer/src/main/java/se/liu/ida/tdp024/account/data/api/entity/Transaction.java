package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {

    public long getID();

    public void setID(long id);

    public String getTransactionType();

    public void setTransactionType(String transactionType);

    public int getAmount();

    public void setAmount(int amount);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);

    public String getStatus();

    public void setStatus(String status);

    public Account getAccount();

    public void setAccount(Account account);

}
