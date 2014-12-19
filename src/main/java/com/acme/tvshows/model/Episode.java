package com.acme.tvshows.model;

import java.util.List;

public interface Episode {

	int getNumber();

	String getTitle();

	List<Link> getLinks() throws ConnectionException, ParseException;

	Link getLink(String linkId) throws ConnectionException, ParseException, MissingElementException;

}
