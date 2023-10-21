package se.liu.ida.tdp024.account.utils.impl.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.utils.api.helpers.HTTPHelper;

public class HTTPHelperImpl implements HTTPHelper {

    // private AccountLogger accountLogger = new AccountLoggerImpl();

    @Override
    public String get(String endpoint, String... parameters) throws Exception {

        String urlToRead = createURL(endpoint, parameters);

        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URI(urlToRead).toURL();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (FileNotFoundException e) {
            throw new AccountServiceConfigurationException("Unable to make a connection to: " + e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        return result;

    }

    private String createURL(String endpoint, String... parameters) {

        StringBuilder urlBuilder = new StringBuilder(endpoint);
        for (int i = 0; i < (parameters.length - 1); i += 2) {
            try {
                String parameterName = URLEncoder.encode(parameters[i], "UTF-8");
                String parameterValue = URLEncoder.encode(parameters[i + 1], "UTF-8");

                if (i == 0) {
                    urlBuilder.append("?");
                } else {
                    urlBuilder.append("&");
                }

                urlBuilder.append(parameterName).append("=").append(parameterValue);
            } catch (Exception e) {
                //accountLogger.log(e);
            }

        }

        return urlBuilder.toString();

    }
}
