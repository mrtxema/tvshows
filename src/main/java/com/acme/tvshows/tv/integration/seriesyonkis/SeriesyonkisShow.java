package com.acme.tvshows.tv.integration.seriesyonkis;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.tv.model.*;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisShow implements Show {
	private final ParseHelper parseHelper;
	private final String showUrlPattern;
	private final String nameSelect;
	private final String seasonSelect;

	private String id;
	private String name;
	private Map<Integer,Season> seasons;


	private SeriesyonkisShow(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		showUrlPattern = config.getShowUrlPattern();
		nameSelect = config.getShowNameSelect();
		seasonSelect = config.getShowSeasonSelect();
	}

	SeriesyonkisShow(ParseHelper parseHelper, String id) throws ShowStoreException {
		this(parseHelper);
		this.id = id;
		retrieveData();
	}

	SeriesyonkisShow(ParseHelper parseHelper, String id, String name) {
		this(parseHelper);
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Season> getSeasons() throws ShowStoreException {
		if (seasons == null) {
			retrieveData();
		}
		return new ArrayList<Season>(seasons.values());
	}

	public Season getSeason(int seasonNumber) throws ShowStoreException {
		if (seasons == null) {
			retrieveData();
		}
		if (seasons.containsKey(seasonNumber)) {
			return seasons.get(seasonNumber);
		} else {
			throw new MissingElementException("Can't find season " + seasonNumber);
		}
	}

	private synchronized void retrieveData() throws ShowStoreException {
		if (this.seasons == null) {
			Document document = null;
			try {
				document = parseHelper.parseUrl(buildUrl(id));
			} catch (ConnectionException e) {
				throw new ShowStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
			} catch (ParseException e) {
				throw new ShowStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
			}
			this.name = document.select(nameSelect).first().text();
			Map<Integer,Season> seasonMap = new TreeMap<Integer,Season>();
			for (Element seasonElement : document.select(seasonSelect)) {
				Season season = new SeriesyonkisSeason(parseHelper, seasonElement);
				seasonMap.put(season.getNumber(), season);
			}
			this.seasons = seasonMap;
		}
	}

	private String buildUrl(String id) {
		return String.format(showUrlPattern, id);
	}
}
