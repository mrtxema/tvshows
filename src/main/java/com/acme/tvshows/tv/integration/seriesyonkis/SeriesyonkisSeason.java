package com.acme.tvshows.tv.integration.seriesyonkis;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.tv.model.Episode;
import com.acme.tvshows.tv.model.Season;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import org.jsoup.nodes.Element;

public class SeriesyonkisSeason implements Season {
	private final ParseHelper parseHelper;
	private final String numberAttr;
	private final String episodeSelect;

	private final Element element;
	private Map<Integer,Episode> episodes;


	public SeriesyonkisSeason(ParseHelper parseHelper, Element element) {
        this.parseHelper = parseHelper;
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		this.numberAttr = config.getSeasonNumberAttr();
		this.episodeSelect = config.getSeasonEpisodeSelect();
		this.element = element;
	}

	public int getNumber() {
		return Integer.parseInt(element.attr(numberAttr));
	}

	public List<Episode> getEpisodes() {
		if (episodes == null) {
			buildEpisodes();
		}
		return new ArrayList<Episode>(episodes.values());
	}

	public Episode getEpisode(int episodeNumber) {
		if (episodes == null) {
			buildEpisodes();
		}
		return episodes.get(episodeNumber);
	}

	private synchronized void buildEpisodes() {
		if (episodes == null) {
			Map<Integer,Episode> episodeMap = new TreeMap<Integer,Episode>();
			for (Element episodeElement : element.parent().select(episodeSelect)) {
				Episode episode = new SeriesyonkisEpisode(parseHelper, episodeElement);
				episodeMap.put(episode.getNumber(), episode);
			}
			episodes = episodeMap;
		}
	}
}
