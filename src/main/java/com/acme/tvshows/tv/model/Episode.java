package com.acme.tvshows.tv.model;

import java.util.List;

public interface Episode {

	int getNumber();

	String getTitle();

	List<Link> getLinks() throws ShowStoreException;

	Link getLink(String linkId) throws ShowStoreException;

}
