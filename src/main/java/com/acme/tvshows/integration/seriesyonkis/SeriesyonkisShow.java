package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Season;
import com.acme.tvshows.model.Show;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisShow implements Show {
	private final String showUrlPattern;
	private final String nameSelect;
	private final String seasonSelect;

	private String id;
	private String name;
	private Map<Integer,Season> seasons;


	private SeriesyonkisShow() {
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		showUrlPattern = config.getShowUrlPattern();
		nameSelect = config.getShowNameSelect();
		seasonSelect = config.getShowSeasonSelect();
	}

	SeriesyonkisShow(String id) throws ConnectionException, ParseException {
		this();
		this.id = id;
		retrieveData();
	}

	SeriesyonkisShow(String id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Season> getSeasons() throws ConnectionException, ParseException {
		if (seasons == null) {
			retrieveData();
		}
		return new ArrayList<Season>(seasons.values());
	}

	public Season getSeason(int seasonNumber) throws ConnectionException, ParseException {
		if (seasons == null) {
			retrieveData();
		}
		return seasons.get(seasonNumber);
	}

	private synchronized void retrieveData() throws ConnectionException, ParseException {
		if (this.seasons == null) {
			Document document = ParseHelper.getInstance().parseUrl(buildUrl(id));
			this.name = document.select(nameSelect).first().text();
			Map<Integer,Season> seasonMap = new TreeMap<Integer,Season>();
			for (Element seasonElement : document.select(seasonSelect)) {
				Season season = new SeriesyonkisSeason(seasonElement);
				seasonMap.put(season.getNumber(), season);
			}
			this.seasons = seasonMap;
		}
	}

	private String buildUrl(String id) {
		return String.format(showUrlPattern, id);
	}
}
