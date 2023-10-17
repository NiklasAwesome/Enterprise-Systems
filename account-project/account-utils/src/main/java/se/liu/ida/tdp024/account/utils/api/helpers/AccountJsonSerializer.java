package se.liu.ida.tdp024.account.utils.api.helpers;

import java.util.List;

public interface AccountJsonSerializer {

    public <T> T fromJson(String json, Class<T> clazz);

    public <T> List<T> fromJsonList(String json, Class<T> clazz);

    public String toJson(Object object);
}
