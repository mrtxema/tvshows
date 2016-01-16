package com.acme.tvshows.tv.integration.seriesyonkis;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.tv.model.ErrorType;
import com.acme.tvshows.tv.model.ShowStoreException;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.tv.model.Show;
import com.acme.tvshows.tv.model.Store;

import java.util.*;

public class SeriesyonkisStore implements Store {
	private final ParseHelper parseHelper;
	private final String searchUrl;
	private final String name;

	public SeriesyonkisStore() {
        parseHelper = new ParseHelper();
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		searchUrl = config.getStoreSearchUrl();
		name = config.getStoreName();
	}

	public String getName() {
		return name;
	}

	public List<Show> findShows(String showName) throws ShowStoreException {
        SearchResponse response = null;
        try {
            response = parseHelper.parseJson(searchUrl, Collections.singletonMap("terms", showName), SearchResponse.class);
        } catch (ConnectionException e) {
            throw new ShowStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
        } catch (ParseException e) {
            throw new ShowStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
        }
        List<Show> shows = new ArrayList<Show>();
		if (response.estado) {
			for (SearchResult result : response.results) {
				if ("serie".equals(result.type)) {
					shows.add(new SeriesyonkisShow(parseHelper, getIdFromUrl(result.url), result.name));
				}
			}
		}
		return shows;
	}

	private String getIdFromUrl(String url) {
		return url.substring(url.lastIndexOf('/') + 1);
	}

	public Show getShow(String id) throws ShowStoreException {
        return new SeriesyonkisShow(parseHelper, id);
    }

	@Override
	public boolean login(Map<String, String> parameters) {
		return true;
	}

	static class SearchResponse {
		public boolean estado;
		public String datos;
		public List<SearchResult> results;
	}

	static class SearchResult {
		public String name;
		public String img;
		public String url;
		public String type;
	}
}
