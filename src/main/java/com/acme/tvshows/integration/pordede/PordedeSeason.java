package com.acme.tvshows.integration.pordede;

import com.acme.tvshows.integration.ParseHelper;
import com.acme.tvshows.model.*;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PordedeSeason implements Season {
    private final ParseHelper parseHelper;
    private final Element element;
    private final int seasonNumber;
    private final String episodeSelect;
    private Map<Integer,Episode> episodes;

    public PordedeSeason(ParseHelper parseHelper, Element seasonElement) {
        this.parseHelper = parseHelper;
        this.element = seasonElement;
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        this.seasonNumber = Integer.parseInt(element.select(config.getSeasonNumberSelect()).first().attr(config.getSeasonNumberAttr()));
        this.episodeSelect = config.getSeasonEpisodeSelect();
    }

    @Override
    public int getNumber() {
        return seasonNumber;
    }

    @Override
    public List<Episode> getEpisodes() {
        if (episodes == null) {
            buildEpisodes();
        }
        return new ArrayList<>(episodes.values());
    }

    @Override
    public Episode getEpisode(int episodeNumber) throws MissingElementException {
        if (episodes == null) {
            buildEpisodes();
        }
        if (episodes.containsKey(episodeNumber)) {
            return episodes.get(episodeNumber);
        } else {
            throw new MissingElementException("Can't find episode " + episodeNumber);
        }
    }

    private synchronized void buildEpisodes() {
        if (episodes == null) {
            Map<Integer,Episode> episodeMap = new TreeMap<Integer,Episode>();
            for (Element episodeElement : element.select(episodeSelect)) {
                Episode episode = new PordedeEpisode(parseHelper, episodeElement);
                episodeMap.put(episode.getNumber(), episode);
            }
            episodes = episodeMap;
        }
    }
}
