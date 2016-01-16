package com.acme.tvshows.movies.model;

import java.util.List;

public interface Movie {
    String getId();

    String getName();

    List<MovieLink> getLinks() throws MovieStoreException;

    MovieLink getLink(String linkId) throws MovieStoreException;
}
