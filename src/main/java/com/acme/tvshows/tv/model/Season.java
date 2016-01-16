package com.acme.tvshows.tv.model;

import java.util.List;

public interface Season {

	int getNumber();

	List<Episode> getEpisodes() throws ShowStoreException;

	Episode getEpisode(int episodeNumber) throws ShowStoreException;

}
