package se.liu.ida.tdp024.account.utils.api.helpers;

public interface HTTPHelper {

    public String get(String endpoint, String... parameters);

    public String postJSON(String endpoint, String[] queryParameters, String[] dataParameters);
}
