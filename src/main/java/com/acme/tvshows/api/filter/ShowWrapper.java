package com.acme.tvshows.api.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.MissingElementException;
import com.acme.tvshows.model.Season;
import com.acme.tvshows.model.Show;

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

	public List<Season> getSeasons() throws ConnectionException, ParseException {
		return show.getSeasons();
	}

	public Season getSeason(int seasonNumber) throws ConnectionException, ParseException, MissingElementException {
		return show.getSeason(seasonNumber);
	}
}
