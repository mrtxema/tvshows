package com.acme.tvshows.model;

import java.net.URL;

public interface Link {

	String getId();

	String getServer();

	Language getLanguage();

	URL getUrl() throws ConnectionException, ParseException;
}
