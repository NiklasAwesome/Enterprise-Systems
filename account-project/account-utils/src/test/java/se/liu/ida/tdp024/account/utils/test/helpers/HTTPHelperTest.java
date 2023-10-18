package se.liu.ida.tdp024.account.utils.test.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.liu.ida.tdp024.account.utils.api.helpers.HTTPHelper;
import se.liu.ida.tdp024.account.utils.impl.helpers.HTTPHelperImpl;

public class HTTPHelperTest {
    private HTTPHelper httpHelper = new HTTPHelperImpl();

    @Test
    public void getTest() {
        String response = httpHelper.get("http://localhost:8070/bank/find.name", "name", "SWEDBANK");
        assertEquals("{\"key\":\"1\",\"name\":\"SWEDBANK\"}", response);
    
        String responseparam = httpHelper.get("http://localhost:8070/bank/find.name", "name", "SWEDBANK", "key", "0984");
        assertEquals("{\"key\":\"1\",\"name\":\"SWEDBANK\"}", responseparam);
    
        String failedresponse = httpHelper.get("http://localhost:8070/banks/find.name", "name", "SWEDBANK");
        assertEquals("", failedresponse);
    }
}
