package com.acme.tvshows.movies.integration.pordede;

import com.acme.tvshows.movies.model.*;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class PordedeMovie implements Movie {
    private static final int NAME_PREFIX_LENGTH = 11;
    private final ParseHelper parseHelper;
    private final String movieUrlPattern;
    private final String nameSelect;

    private String id;
    private String name;
    private Map<String, MovieLink> links;

    private PordedeMovie(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
        PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
        this.movieUrlPattern = config.getMovieUrlPattern();
        this.nameSelect = config.getMovieNameSelect();
    }

    public PordedeMovie(ParseHelper parseHelper, Element movieElement) {
        this(parseHelper);
        PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
        this.id = getLastPathComponent(movieElement.attr(config.getMovieSearchIdAttr()));
        this.name = movieElement.select(config.getMovieSearchNameSelect()).first().attr(config.getMovieSearchNameAttr());
    }

    public PordedeMovie(ParseHelper parseHelper, String id) throws MovieStoreException {
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
    public List<MovieLink> getLinks() throws MovieStoreException {
        if (links == null) {
            retrieveData();
        }
        return new ArrayList<>(links.values());
    }

    @Override
    public MovieLink getLink(String linkId) throws MovieStoreException {
        if (links == null) {
            retrieveData();
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        } else {
            throw new MissingElementException("Can't find link " + linkId);
        }
    }

    private synchronized void retrieveData() throws MovieStoreException {
        if (links == null) {
            PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
            Document document = null;
            try {
                document = parseHelper.parseUrl(String.format(movieUrlPattern, id));
            } catch (ConnectionException e) {
                throw new MovieStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
            } catch (ParseException e) {
                throw new MovieStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
            }
            this.name = document.select(nameSelect).first().ownText().substring(NAME_PREFIX_LENGTH);
            Map<String, MovieLink> linkMap = new LinkedHashMap<>();
            for (Element linkElement : document.select(config.getMovieRealLinkSelect())) {
                MovieLink link = new PordedeMovieLink(parseHelper, linkElement);
                linkMap.put(link.getId(), link);
            }
            links = linkMap;
        }
    }
}
