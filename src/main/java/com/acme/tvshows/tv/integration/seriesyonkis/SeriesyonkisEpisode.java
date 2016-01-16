package com.acme.tvshows.tv.integration.seriesyonkis;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.tv.model.*;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisEpisode implements Episode {
	private final ParseHelper parseHelper;
	private final String numberSelect;
	private final String linkAttr;
	private final String realLinkSelect;

	private final Element element;
	private List<Link> links;


	public SeriesyonkisEpisode(ParseHelper parseHelper, Element element) {
        this.parseHelper = parseHelper;
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

	public String getTitle() {
		String title = element.ownText().trim();
		return (title.charAt(0) == '-') ? title.substring(1).trim() : title;
	}

	public List<Link> getLinks() throws ShowStoreException {
		if (links == null) {
			buildLinks(element.attr(linkAttr));
		}
		return links;
	}

	public Link getLink(String linkId) throws ShowStoreException {
		if (links == null) {
			buildLinks(element.attr(linkAttr));
		}
		for (Link link : links) {
			if (link.getId().equals(linkId)) {
				return link;
			}
		}
		throw new MissingElementException("Can't find link with id " + linkId);
	}

	private synchronized void buildLinks(String url) throws ShowStoreException {
		if (links == null) {
			Document document = null;
			try {
				document = parseHelper.parseUrl(url);
			} catch (ConnectionException e) {
				throw new ShowStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
			} catch (ParseException e) {
				throw new ShowStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
			}
			List<Link> linkList = new ArrayList<Link>();
			for (Element linkElement : document.select(realLinkSelect)) {
				linkList.add(new SeriesyonkisLink(parseHelper, linkElement));
			}
			links = linkList;
		}
	}
}
