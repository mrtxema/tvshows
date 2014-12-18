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
	private static final ParseHelper INSTANCE = new ParseHelper();

	private ParseHelper() {
	}

	public static ParseHelper getInstance() {
		return INSTANCE;
	}

	public Document parseUrl(String url) throws ConnectionException, ParseException {
		Connection.Response response;
		try {
			response = Jsoup.connect(url).execute();
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
			Connection connection = Jsoup.connect(url).ignoreContentType(true).method(Connection.Method.POST);
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
