package se.liu.ida.tdp024.account.utils.test.exception;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

import se.liu.ida.tdp024.account.utils.api.exception.AccountEntityNotFoundException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInputParameterException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountInsufficentHoldingsException;
import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;

public class AccountExceptionTest {
    @Test
    public void testAccountEntityNotFoundException() {
        assertThrows(AccountEntityNotFoundException.class, () -> {
            throw new AccountEntityNotFoundException("Test");
        });
    }
    @Test
    public void testAccountInputParameterException() {
        assertThrows(AccountInputParameterException.class, () -> {
            throw new AccountInputParameterException("Test");
        });
    }
    @Test
    public void testAccountInsufficentHoldingsException() {
        assertThrows(AccountInsufficentHoldingsException.class, () -> {
            throw new AccountInsufficentHoldingsException("Test");
        });
    }
    @Test
    public void testAccountServiceConfigurationException() {
        assertThrows(AccountServiceConfigurationException.class, () -> {
            throw new AccountServiceConfigurationException("Test");
        });
    }
}
