package com.acme.tvshows.integration.pordede;

import com.acme.tvshows.integration.ParseHelper;
import com.acme.tvshows.model.*;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class PordedeShow implements Show {
    private final ParseHelper parseHelper;
    private final String showUrlPattern;
    private final String nameSelect;
    private final String seasonSelect;

    private String id;
    private String name;
    private Map<Integer,Season> seasons;

    private PordedeShow(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        showUrlPattern = config.getShowUrlPattern();
        nameSelect = config.getShowNameSelect();
        seasonSelect = config.getShowSeasonSelect();
    }

    public PordedeShow(ParseHelper parseHelper, Element showElement) {
        this(parseHelper);
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        this.id = getLastPathComponent(showElement.attr(config.getShowSearchIdAttr()));
        this.name = showElement.select(config.getShowSearchNameSelect()).first().attr(config.getShowSearchNameAttr());
    }

    public PordedeShow(ParseHelper parseHelper, String id) throws ConnectionException, ParseException {
        this(parseHelper);
        this.id = id;
        retrieveData();
    }

    private String getLastPathComponent(String path) {
        final int index = path.lastIndexOf('/');
        return (index == -1) ? path : path.substring(index + 1);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Season> getSeasons() throws ConnectionException, ParseException {
        if (seasons == null) {
            retrieveData();
        }
        return new ArrayList<>(seasons.values());
    }

    @Override
    public Season getSeason(int seasonNumber) throws ConnectionException, ParseException, MissingElementException {
        if (seasons == null) {
            retrieveData();
        }
        if (seasons.containsKey(seasonNumber)) {
            return seasons.get(seasonNumber);
        } else {
            throw new MissingElementException("Can't find season " + seasonNumber);
        }
    }

    private synchronized void retrieveData() throws ConnectionException, ParseException {
        if (this.seasons == null) {
            Document document = parseHelper.parseUrl(buildUrl(id));
            this.name = document.select(nameSelect).first().ownText();
            Map<Integer,Season> seasonMap = new TreeMap<Integer,Season>();
            for (Element seasonElement : document.select(seasonSelect)) {
                Season season = new PordedeSeason(parseHelper, seasonElement);
                seasonMap.put(season.getNumber(), season);
            }
            this.seasons = seasonMap;
        }
    }

    private String buildUrl(String id) {
        return String.format(showUrlPattern, id);
    }
}
