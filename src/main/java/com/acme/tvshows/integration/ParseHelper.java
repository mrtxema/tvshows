package com.acme.tvshows.integration;

import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseHelper {
	private static final String DEFAULT_REFERRER = "http://www.google.com/";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36";

    private Map<String, String> cookies = new HashMap<>();
    private String lastUrl;

    private String getReferrerUrl(String url) {
        String result = lastUrl;
        if (result == null) {
            int idx = url.indexOf('/', 8);
            result = (idx == -1) ? DEFAULT_REFERRER : url.substring(0, idx + 1);
        }
        lastUrl = url;
        return result;
    }

	private Connection connect(String url) {
        //System.out.printf("Connecting to url: %s%n", url);
		return Jsoup.connect(url).userAgent(USER_AGENT).referrer(getReferrerUrl(url)).cookies(cookies);
	}

    public int getResponseCode(String url) throws ConnectionException {
        try {
            Connection.Response response = connect(url).followRedirects(false).execute();
            cookies.putAll(response.cookies());
            return response.statusCode();
        } catch (IOException ioe) {
            throw new ConnectionException(String.format("Can't connect to '%s'", url), ioe);
        }
    }

	public int getPostResponseCode(String url, Map<String,String> parameters) throws ConnectionException {
        try {
            Connection connection = connect(url).followRedirects(false).method(Connection.Method.POST);
            for (Map.Entry<String,String> entry : parameters.entrySet()) {
                connection.data(entry.getKey(), entry.getValue());
            }
            Connection.Response response = connection.execute();
            cookies.putAll(response.cookies());
            return response.statusCode();
        } catch (IOException ioe) {
            throw new ConnectionException(String.format("Can't connect to '%s'", url), ioe);
        }
    }

	public Document parseUrl(String url) throws ConnectionException, ParseException {
		Connection.Response response;
		try {
			response = connect(url).execute();
            cookies.putAll(response.cookies());
		} catch (IOException ioe) {
			throw new ConnectionException(String.format("Can't connect to '%s'", url), ioe);
		}
		try {
			return response.parse();
		} catch (IOException ioe) {
			throw new ParseException(String.format("Can't parse html at '%s'", url), ioe);
		}
	}

    public <T> T parseJson(String url, Map<String,String> parameters, Class<T> clazz) throws ConnectionException, ParseException {
        String responseJson;
        try {
            Connection connection = connect(url).ignoreContentType(true).method(Connection.Method.POST);
            for (Map.Entry<String,String> entry : parameters.entrySet()) {
                connection.data(entry.getKey(), entry.getValue());
            }
            Connection.Response response = connection.execute();
            cookies.putAll(response.cookies());
            responseJson = response.body();
        } catch (IOException ioe) {
            throw new ConnectionException(String.format("Can't connect to '%s'", url), ioe);
        }
        try {
            return new Gson().fromJson(responseJson, clazz);
        } catch (JsonSyntaxException e) {
            throw new ParseException(String.format("Can't parse json at '%s'", url), e);
        }
    }
}
