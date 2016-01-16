package com.acme.tvshows.tv.model;

import java.util.List;
import java.util.Map;

public interface Store {

	String getName();

	List<Show> findShows(String showName) throws ShowStoreException;

	Show getShow(String id) throws ShowStoreException;

	boolean login(Map<String, String> parameters) throws ShowStoreException;
}
