package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {

    public long getID();

    public String getPersonKey();

    public void setPersonKey(String personKey);

    public String getAccountType();

    public void setAccountType(String accountType);

    public String getBankKey();

    public void setBankKey(String bankKey);

    public int getHoldings();

    public void setHoldnings(int holdings);
    
}
