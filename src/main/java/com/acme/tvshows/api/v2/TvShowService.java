package com.acme.tvshows.api.v2;

import com.acme.tvshows.api.v2.model.BasicEpisode;
import com.acme.tvshows.api.v2.model.BasicLink;
import com.acme.tvshows.api.v2.model.BasicShow;
import com.acme.tvshows.api.v2.model.BasicStore;
import com.acme.tvshows.integration.StoreManager;
import com.acme.tvshows.integration.StoreType;
import com.acme.tvshows.model.*;
import com.acme.tvshows.util.BeanFactory;

import java.util.*;

public class TvShowService {
	private static final int API_VERSION = 2;
	private static final int MINIMUM_SEARCH_LENGTH = 3;
	private final TvShowFilterManager filterManager = new TvShowFilterManager();

	private int parseInt(String s) throws ShowStoreException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ShowStoreException(ErrorType.INVALID_ARGUMENT, "Invalid number: " + s, e);
		}
	}

	private Map<String, String> firstValueMap(Map<String, String[]> parameters) {
		Map<String, String> result = new HashMap<>();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			result.put(entry.getKey(), entry.getValue()[0]);
		}
		return result;
	}

	public List<BasicStore> getAllStoreTypes() {
		List<BasicStore> result = new ArrayList<>();
		for (StoreType type : StoreType.getValues(API_VERSION)) {
			result.add(new BasicStore(type));
		}
		return result;
	}

	public String login(String storeCode, Map<String, String[]> parameters) throws ShowStoreException {
        return BeanFactory.getInstance(StoreManager.class).newStore(storeCode, firstValueMap(parameters));
	}

	public List<BasicShow> findShows(String storeCode, String token, String searchString) throws ShowStoreException {
		if (searchString == null || searchString.length() < MINIMUM_SEARCH_LENGTH) {
			throw new ShowStoreException(ErrorType.INVALID_ARGUMENT, String.format("Invalid searchString '%s'. Minimum length is %d", searchString, MINIMUM_SEARCH_LENGTH));
		}
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		List<Show> shows = store.findShows(searchString);
		List<BasicShow> result = new ArrayList<BasicShow>();
		for (Show show : filterManager.filter(Show.class, shows)) {
			result.add(new BasicShow(show));
		}
		return result;
	}

	public List<Integer> getShowSeasons(String storeCode, String token, String show) throws ShowStoreException {
		List<Integer> result = new ArrayList<Integer>();
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		for (Season season : store.getShow(show).getSeasons()) {
			result.add(season.getNumber());
		}
		return result;
	}

	public List<BasicEpisode> getSeasonEpisodes(String storeCode, String token, String show, String season) throws ShowStoreException {
		List<BasicEpisode> result = new ArrayList<BasicEpisode>();
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		List<Episode> episodes = store.getShow(show).getSeason(parseInt(season)).getEpisodes();
		for (Episode episode : filterManager.filter(Episode.class, episodes)) {
			result.add(new BasicEpisode(episode));
		}
		return result;
	}

	public List<BasicLink> getEpisodeLinks(String storeCode, String token, String show, String season, String episode) throws ShowStoreException {
		List<BasicLink> result = new ArrayList<BasicLink>();
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		List<Link> links = store.getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLinks();
		for (Link link : filterManager.filter(Link.class, links)) {
			result.add(new BasicLink(link));
		}
		return result;
	}

	public String getLinkUrl(String storeCode, String token, String show, String season, String episode, String link) throws ShowStoreException {
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		return store.getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLink(link).getUrl().toString();
	}
}
