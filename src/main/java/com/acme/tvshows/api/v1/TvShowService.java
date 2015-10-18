package com.acme.tvshows.api.v1;

import com.acme.tvshows.api.filter.BasicShowFilter;
import com.acme.tvshows.api.filter.TvShowAttributeValue;
import com.acme.tvshows.api.filter.TvShowFilter;
import com.acme.tvshows.integration.StoreType;
import com.acme.tvshows.model.Episode;
import com.acme.tvshows.model.Language;
import com.acme.tvshows.model.Link;
import com.acme.tvshows.model.Season;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;
import com.acme.tvshows.model.ShowStoreException;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TvShowService {
	private static final int MINIMUM_SEARCH_LENGTH = 3;
	private final Map<Class<?>, List<TvShowFilter<?>>> filters;

	public TvShowService() {
		filters = retrieveFilters();
	}

	private Map<Class<?>, List<TvShowFilter<?>>> retrieveFilters() {
		Map<Class<?>, List<TvShowFilter<?>>> result = new HashMap<Class<?>, List<TvShowFilter<?>>>();
		result.put(Show.class, Collections.singletonList(
			new BasicShowFilter(Arrays.asList(new TvShowAttributeValue("id", "big_bang_theory")), Arrays.asList(new TvShowAttributeValue("id", "the-big-bang-theory")))
		));
		return result;
	}

	private <T> List<T> filter(Class<T> clazz, List<T> beans) throws ShowStoreException {
		if (!filters.containsKey(clazz)) {
			return beans;
		}
		List<T> result = beans;
		for (TvShowFilter<?> filterItem : filters.get(clazz)) {
			TvShowFilter<T> filter = (TvShowFilter<T>) filterItem;
			List<T> filtered = new ArrayList<T>();
			for (T bean : result) {
				filtered.addAll(filter.filter(bean));
			}
			result = filtered;
		}
		return result;
	}

	public Store getStore(String store) throws ShowStoreException {
        StoreType type = StoreType.fromCode(store);
        if (type == null) {
            throw new ShowStoreException("Invalid store name: " + store);
        }
		try {
			return type.getStoreClass().newInstance();
		} catch (InstantiationException | IllegalAccessException  e) {
			throw new ShowStoreException(String.format("Can't build %s store instance", store), e);
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
		for (StoreType factory : StoreType.values()) {
			result.add(factory.getCode());
		}
		return result;
	}

	public List<BasicShow> findShows(String store, String searchString) throws ShowStoreException {
		if (searchString == null || searchString.length() < MINIMUM_SEARCH_LENGTH) {
			throw new ShowStoreException(String.format("Invalid searchString '%s'. Minimum length is %d", searchString, MINIMUM_SEARCH_LENGTH));
		}
		List<Show> shows = getStore(store).findShows(searchString);
		List<BasicShow> result = new ArrayList<BasicShow>();
		for (Show show : filter(Show.class, shows)) {
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
		List<Episode> episodes = getStore(store).getShow(show).getSeason(parseInt(season)).getEpisodes();
		for (Episode episode : filter(Episode.class, episodes)) {
			result.add(new BasicEpisode(episode));
		}
		return result;
	}

	public List<BasicLink> getEpisodeLinks(String store, String show, String season, String episode) throws ShowStoreException {
		List<BasicLink> result = new ArrayList<BasicLink>();
		List<Link> links = getStore(store).getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLinks();
		for (Link link : filter(Link.class, links)) {
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
