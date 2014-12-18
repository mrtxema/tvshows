package com.acme.tvshows.model;

import java.net.URL;

public interface Link {

	URL getUrl() throws ConnectionException, ParseException;

	String getServer();

	Language getLanguage();
}
