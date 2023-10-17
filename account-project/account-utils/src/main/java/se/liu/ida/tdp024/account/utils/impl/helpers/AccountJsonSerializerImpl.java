package se.liu.ida.tdp024.account.utils.impl.helpers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import se.liu.ida.tdp024.account.utils.api.helpers.AccountJsonSerializer;

public class AccountJsonSerializerImpl implements AccountJsonSerializer {

    private Gson gson;

    public AccountJsonSerializerImpl() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> List<T> fromJsonList(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        // Type clazzType = TypeToken.get(clazz).getType();
        // Type listType = new TypeToken<List<clazzType>>(){}.getType();
        return gson.fromJson(json, type);

    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
