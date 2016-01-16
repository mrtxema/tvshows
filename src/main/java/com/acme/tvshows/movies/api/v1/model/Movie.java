package com.acme.tvshows.movies.api.v1.model;

public class Movie {
    final String id;
    final String name;

    public Movie(com.acme.tvshows.movies.model.Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
    }
}
