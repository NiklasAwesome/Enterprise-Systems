package se.liu.ida.tdp024.account.utils.test.logger;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se.liu.ida.tdp024.account.utils.api.logger.AccountLogger;
import se.liu.ida.tdp024.account.utils.impl.logger.AccountLoggerKafka;

public class AccountLoggerTest {
    private AccountLogger al = new AccountLoggerKafka("rest");

    @Test
    public void logtest() {
        al.log("hello i am a test");
        assertTrue(true);
    }
}
