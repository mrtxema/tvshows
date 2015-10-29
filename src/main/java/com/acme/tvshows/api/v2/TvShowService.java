package com.acme.tvshows.api.v2;

import com.acme.tvshows.api.filter.BasicShowFilter;
import com.acme.tvshows.api.filter.PreferredServerLinkFilter;
import com.acme.tvshows.api.filter.TvShowAttributeValue;
import com.acme.tvshows.api.filter.TvShowFilter;
import com.acme.tvshows.integration.StoreManager;
import com.acme.tvshows.integration.StoreType;
import com.acme.tvshows.model.*;
import com.acme.tvshows.util.BeanFactory;

import java.util.*;

public class TvShowService {
	private static final int MINIMUM_SEARCH_LENGTH = 3;
	private final Map<Class<?>, List<TvShowFilter<?>>> filters;

	public TvShowService() {
		filters = retrieveFilters();
	}

	private Map<Class<?>, List<TvShowFilter<?>>> retrieveFilters() {
		Map<Class<?>, List<TvShowFilter<?>>> result = new HashMap<Class<?>, List<TvShowFilter<?>>>();
		result.put(Show.class, Collections.singletonList(
			new BasicShowFilter(Collections.singletonList(new TvShowAttributeValue("id", "big_bang_theory")), Collections.singletonList(new TvShowAttributeValue("id", "the-big-bang-theory")))
		));
        result.put(Link.class, Collections.singletonList(
           new PreferredServerLinkFilter(Collections.singletonList("streamcloud"))
        ));
		return result;
	}

	private <T> List<T> filter(Class<T> clazz, List<T> beans) throws ShowStoreException {
		if (!filters.containsKey(clazz) || beans.isEmpty()) {
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
		return result.isEmpty() ? beans : result;
	}

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
		for (StoreType type : StoreType.values()) {
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
		for (Show show : filter(Show.class, shows)) {
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
		for (Episode episode : filter(Episode.class, episodes)) {
			result.add(new BasicEpisode(episode));
		}
		return result;
	}

	public List<BasicLink> getEpisodeLinks(String storeCode, String token, String show, String season, String episode) throws ShowStoreException {
		List<BasicLink> result = new ArrayList<BasicLink>();
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		List<Link> links = store.getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLinks();
		for (Link link : filter(Link.class, links)) {
			result.add(new BasicLink(link));
		}
		return result;
	}

	public String getLinkUrl(String storeCode, String token, String show, String season, String episode, String link) throws ShowStoreException {
        Store store = BeanFactory.getInstance(StoreManager.class).getStore(storeCode, token);
		return store.getShow(show).getSeason(parseInt(season)).getEpisode(parseInt(episode)).getLink(link).getUrl().toString();
	}

	static class BasicStore {
		final String code;
        final List<String> loginParameters;

        BasicStore(StoreType storeType) {
            code = storeType.getCode();
            loginParameters = storeType.getLoginParameters();
        }
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
