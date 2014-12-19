package com.acme.tvshows.api;

import com.acme.tvshows.integration.StoreFactory;
import com.acme.tvshows.model.Episode;
import com.acme.tvshows.model.Link;
import com.acme.tvshows.model.Season;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;
import com.acme.tvshows.model.ShowStoreException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TvShowService {

	public Set<StoreFactory> getAllStores() {
		return EnumSet.allOf(StoreFactory.class);
	}

	private Store getStore(String store) throws ShowStoreException {
		try {
			return StoreFactory.valueOf(store).newStore();
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

	public List<BasicShow> findShows(String store, String searchString) throws ShowStoreException {
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

	public List<Integer> getSeasonEpisodes(String store, String show, String season) throws ShowStoreException {
		List<Integer> result = new ArrayList<Integer>();
		for (Episode episode : getStore(store).getShow(show).getSeason(parseInt(season)).getEpisodes()) {
			result.add(episode.getNumber());
		}
		return result;
	}

	public List<Link> getEpisodeLinks(String store, String show, String season, String episode) throws ShowStoreException {
		return getStore(store).getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLinks();
	}

	static class BasicShow {
		private final Show show;

		BasicShow(Show show) {
			this.show = show;
		}

		public String getId() {
			return show.getId();
		}

		public String getName() {
			return show.getName();
		}
	}
}
