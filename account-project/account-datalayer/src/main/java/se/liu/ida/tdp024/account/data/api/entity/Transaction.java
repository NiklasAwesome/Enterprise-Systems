package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {

    public long getID();

    public String getType();

    public void setType(String type);

    public int getAmount();

    public void setAmount(int amount);

    public Date getCreated();

    public void setCreated(Date created);

    public String getStatus();

    public void setStatus(String status);

    public Account getAccount();

    public void setAccount(Account account);

}
