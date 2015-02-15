package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseHelper {
	private static final String DEFAULT_REFERRER = "http://www.google.com/";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36";
	private static final ParseHelper INSTANCE = new ParseHelper();

	private ParseHelper() {
	}

	public static ParseHelper getInstance() {
		return INSTANCE;
	}

	private Connection connect(String url) {
		int idx = url.indexOf('/', 8);
		String baseUrl = (idx == -1) ? DEFAULT_REFERRER : url.substring(0, idx + 1);
		return Jsoup.connect(url).userAgent(USER_AGENT).referrer(baseUrl);
	}

	public Document parseUrl(String url) throws ConnectionException, ParseException {
		Connection.Response response;
		try {
			response = connect(url).execute();
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
			responseJson = connection.execute().body();
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
