package com.acme.tvshows.integration.pordede;

import com.acme.tvshows.integration.ParseHelper;
import com.acme.tvshows.model.*;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class PordedeEpisode implements Episode {
    private final ParseHelper parseHelper;
    private final int number;
    private final String title;
    private final String episodeUrl;
    private Map<String, Link> links;

    public PordedeEpisode(ParseHelper parseHelper, Element episodeElement) {
        this.parseHelper = parseHelper;
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        this.title = episodeElement.ownText().trim();
        this.number = Integer.parseInt(episodeElement.select(config.getEpisodeNumberSelect()).first().ownText().trim());
        this.episodeUrl = episodeElement.attr(config.getEpisodeLinkAttr());
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<Link> getLinks() throws ConnectionException, ParseException {
        if (links == null) {
            buildLinks();
        }
        return new ArrayList<>(links.values());
    }

    @Override
    public Link getLink(String linkId) throws ConnectionException, ParseException, MissingElementException {
        if (links == null) {
            buildLinks();
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        } else {
            throw new MissingElementException("Can't find link " + linkId);
        }
    }

    private synchronized void buildLinks() throws ConnectionException, ParseException {
        if (links == null) {
            PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
            Document document = parseHelper.parseUrl(episodeUrl);
            Map<String, Link> linkMap = new LinkedHashMap<>();
            for (Element linkElement : document.select(config.getEpisodeRealLinkSelect())) {
                Link link = new PordedeLink(parseHelper, linkElement);
                linkMap.put(link.getId(), link);
            }
            links = linkMap;
        }
    }
}
