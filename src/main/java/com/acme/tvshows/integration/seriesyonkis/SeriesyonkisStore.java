package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeriesyonkisStore implements Store {
	public final String searchUrl;
	public final String name;

	public SeriesyonkisStore() {
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		searchUrl = config.getStoreSearchUrl();
		name = config.getStoreName();
	}

	public String getName() {
		return name;
	}

	public List<Show> findShows(String showName) throws ConnectionException, ParseException {
		SearchResponse response = ParseHelper.getInstance().parseJson(searchUrl, Collections.singletonMap("terms", showName), SearchResponse.class);
		List<Show> shows = new ArrayList<Show>();
		if (response.estado) {
			for (SearchResult result : response.results) {
				if ("serie".equals(result.type)) {
					shows.add(new SeriesyonkisShow(getIdFromUrl(result.url), result.name));
				}
			}
		}
		return shows;
	}

	private String getIdFromUrl(String url) {
		return url.substring(url.lastIndexOf('/') + 1);
	}

	public Show getShow(String id) throws ConnectionException, ParseException {
		return new SeriesyonkisShow(id);
	}

	class SearchResponse {
		public boolean estado;
		public String datos;
		public List<SearchResult> results;
	}

	class SearchResult {
		public String name;
		public String img;
		public String url;
		public String type;
	}
}
