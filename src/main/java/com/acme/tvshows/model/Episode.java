package com.acme.tvshows.model;

import java.util.List;

public interface Episode {

	int getNumber();

	List<Link> getLinks() throws ConnectionException, ParseException;

}
