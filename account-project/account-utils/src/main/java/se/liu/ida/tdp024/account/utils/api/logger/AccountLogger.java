package se.liu.ida.tdp024.account.utils.api.logger;

import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;

public interface AccountLogger {
    public void log(String message) throws AccountServiceConfigurationException;

}
