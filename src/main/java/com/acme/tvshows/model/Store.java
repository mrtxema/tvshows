package com.acme.tvshows.model;

import java.util.List;
import java.util.Map;

public interface Store {

	String getName();

	List<Show> findShows(String showName) throws ConnectionException, ParseException;

	Show getShow(String id) throws ConnectionException, ParseException;

	String login(Map<String, String> parameters);
}
