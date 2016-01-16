package com.acme.tvshows.movies.api.v1.model;

public class MovieLink {
    final String id;
    final String server;
    final Language language;

    public MovieLink(com.acme.tvshows.movies.model.MovieLink link) {
        this.id = link.getId();
        this.server = link.getServer();
        this.language = new Language(link.getLanguage());
    }
}
