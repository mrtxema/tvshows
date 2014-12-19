package com.acme.tvshows.api;

import com.acme.tvshows.integration.StoreFactory;
import com.acme.tvshows.model.Episode;
import com.acme.tvshows.model.Language;
import com.acme.tvshows.model.Link;
import com.acme.tvshows.model.Season;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;
import com.acme.tvshows.model.ShowStoreException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TvShowService {
	private static final int MINIMUM_SEARCH_LENGTH = 3;

	private Store getStore(String store) throws ShowStoreException {
		try {
			return StoreFactory.fromCode(store).newStore();
		} catch (IllegalArgumentException e) {
			throw new ShowStoreException("Invalid store name: " + store, e);
		}
	}

	private int parseInt(String s) throws ShowStoreException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ShowStoreException("Invalid number: " + s, e);
		}
	}

	public Set<String> getAllStores() {
		Set<String> result = new HashSet<String>();
		for (StoreFactory factory : StoreFactory.values()) {
			result.add(factory.getCode());
		}
		return result;
	}

	public List<BasicShow> findShows(String store, String searchString) throws ShowStoreException {
		if (searchString == null || searchString.length() < MINIMUM_SEARCH_LENGTH) {
			throw new ShowStoreException(String.format("Invalid searchString '%s'. Minimum length is %d", searchString, MINIMUM_SEARCH_LENGTH));
		}
		List<BasicShow> result = new ArrayList<BasicShow>();
		for (Show show : getStore(store).findShows(searchString)) {
			result.add(new BasicShow(show));
		}
		return result;
	}

	public List<Integer> getShowSeasons(String store, String show) throws ShowStoreException {
		List<Integer> result = new ArrayList<Integer>();
		for (Season season : getStore(store).getShow(show).getSeasons()) {
			result.add(season.getNumber());
		}
		return result;
	}

	public List<BasicEpisode> getSeasonEpisodes(String store, String show, String season) throws ShowStoreException {
		List<BasicEpisode> result = new ArrayList<BasicEpisode>();
		for (Episode episode : getStore(store).getShow(show).getSeason(parseInt(season)).getEpisodes()) {
			result.add(new BasicEpisode(episode));
		}
		return result;
	}

	public List<BasicLink> getEpisodeLinks(String store, String show, String season, String episode) throws ShowStoreException {
		List<BasicLink> result = new ArrayList<BasicLink>();
		for (Link link : getStore(store).getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLinks()) {
			result.add(new BasicLink(link));
		}
		return result;
	}

	public String getLinkUrl(String store, String show, String season, String episode, String link) throws ShowStoreException {
		return getStore(store).getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLink(link).getUrl().toString();
	}

	static class BasicShow {
		final String id;
		final String name;

		BasicShow(Show show) {
			id = show.getId();
			name = show.getName();
		}
	}

	static class BasicEpisode {
		final int number;
		final String title;

		BasicEpisode(Episode episode) {
			number = episode.getNumber();
			title = episode.getTitle();
		}
	}

	static class BasicLink {
		final String id;
		final String server;
		final BasicLanguage language;

		BasicLink(Link link) throws ShowStoreException {
			id = link.getId();
			server = link.getServer();
			language = new BasicLanguage(link.getLanguage());
		}
	}

	static class BasicLanguage {
		final String code;
		final String name;

		BasicLanguage(Language language) {
			code = language.getCode();
			name = language.getName();
		}
	}
}
