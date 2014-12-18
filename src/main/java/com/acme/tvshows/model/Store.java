package com.acme.tvshows.model;

import java.util.List;

public interface Store {

	String getName();

	List<Show> findShows(String showName) throws ConnectionException, ParseException;

	Show getShow(String id) throws ConnectionException, ParseException;

}
