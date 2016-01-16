package com.acme.tvshows.tv.api.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.tv.model.MissingElementException;
import com.acme.tvshows.tv.model.Season;
import com.acme.tvshows.tv.model.Show;
import com.acme.tvshows.tv.model.ShowStoreException;

public class ShowWrapper implements Show {
	private final Show show;
	private final Map<String,String> updates;

	public ShowWrapper(Show show, List<TvShowAttributeValue> updates) {
		this.show = show;
		this.updates = new HashMap<String,String>();
		for (TvShowAttributeValue attr : updates) {
			this.updates.put(attr.getAttribute(), attr.getValue());
		}
	}

	public String getId() {
		return updates.containsKey("id") ? updates.get("id") : show.getId();
	}

	public String getName() {
		return updates.containsKey("name") ? updates.get("name") : show.getName();
	}

	public List<Season> getSeasons() throws ShowStoreException {
		return show.getSeasons();
	}

	public Season getSeason(int seasonNumber) throws ShowStoreException {
		return show.getSeason(seasonNumber);
	}
}
