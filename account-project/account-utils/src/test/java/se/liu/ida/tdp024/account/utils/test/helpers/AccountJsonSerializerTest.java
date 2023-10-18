package se.liu.ida.tdp024.account.utils.test.helpers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;
import se.liu.ida.tdp024.account.utils.impl.helpers.AccountJsonSerializerImpl;

class TestClass {
    public String name;
    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class AccountJsonSerializerTest {
    AccountJsonSerializer ajs = new AccountJsonSerializerImpl();

    @Test
    public void toJsonTest() {
        List<String> l = new ArrayList<>();
        l.add("1");
        l.add("2");
        l.add("3");
        String json = ajs.toJson(l);

        assertEquals("[\"1\",\"2\",\"3\"]", json);

    }

    @Test
    public void fromJsonTest() {
        String json = "{'name' : 'kalle', 'age' : 20}";

        TestClass tc = ajs.fromJson(json, TestClass.class);

        assertEquals(tc.name, "kalle");
        assertEquals(tc.age, 20);

    }

    @Test
    public void fromJsonListTest() {

        String json = "[{'name' : 'kalle', 'age' : 20}, {'name' : 'jocke', 'age' : 25}]";

        List<TestClass> tc = ajs.fromJsonList(json, TestClass.class);

        assertEquals(tc.get(0).name, "kalle");
        assertEquals(tc.get(0).age, 20);
        assertEquals(tc.get(1).name, "jocke");
        assertEquals(tc.get(1).age, 25);

    }

}
