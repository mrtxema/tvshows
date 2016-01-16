package com.acme.tvshows.tv.model;

import java.util.List;

public interface Show {

	String getId();

	String getName();

	List<Season> getSeasons() throws ShowStoreException;

	Season getSeason(int seasonNumber) throws ShowStoreException;

}
