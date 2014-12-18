package com.acme.tvshows.model;

import java.util.List;

public interface Show {

	String getId();

	String getName();

	List<Season> getSeasons() throws ConnectionException, ParseException;

	Season getSeason(int seasonNumber) throws ConnectionException, ParseException;

}
