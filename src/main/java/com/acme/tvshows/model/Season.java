package com.acme.tvshows.model;

import java.util.List;

public interface Season {

	int getNumber();

	List<Episode> getEpisodes() throws ConnectionException, ParseException;

	Episode getEpisode(int episodeNumber) throws ConnectionException, ParseException, MissingElementException;

}
