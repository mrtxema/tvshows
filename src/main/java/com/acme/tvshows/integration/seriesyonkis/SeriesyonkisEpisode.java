package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Episode;
import com.acme.tvshows.model.Link;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisEpisode implements Episode {
	private final String numberSelect;
	private final String linkAttr;
	private final String realLinkSelect;

	private final Element element;
	private List<Link> links;


	public SeriesyonkisEpisode(Element element) {
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		this.numberSelect = config.getEpisodeNumberSelect();
		this.linkAttr = config.getEpisodeLinkAttr();
		this.realLinkSelect = config.getEpisodeRealLinkSelect();
		this.element = element;
	}

	public int getNumber() {
		String code = element.select(numberSelect).first().ownText().trim();
		return Integer.parseInt(code.split("x")[1]);
	}

	public List<Link> getLinks() throws ConnectionException, ParseException {
		if (links == null) {
			buildLinks(element.attr(linkAttr));
		}
		return links;
	}

	private synchronized void buildLinks(String url) throws ConnectionException, ParseException {
		if (links == null) {
			Document document = ParseHelper.getInstance().parseUrl(url);
			List<Link> linkList = new ArrayList<Link>();
			for (Element linkElement : document.select(realLinkSelect)) {
				linkList.add(new SeriesyonkisLink(linkElement));
			}
			links = linkList;
		}
	}
}
