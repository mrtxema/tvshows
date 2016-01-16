package com.acme.tvshows.tv.model;

import java.net.URL;

public interface Link {

	String getId();

	String getServer();

	Language getLanguage();

	URL getUrl() throws ShowStoreException;
}
