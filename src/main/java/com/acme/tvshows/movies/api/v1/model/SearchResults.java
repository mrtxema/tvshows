package com.acme.tvshows.movies.api.v1.model;

import java.util.List;

public class SearchResults {
    final List<Movie> movies;

    public SearchResults(List<Movie> movies) {
        this.movies = movies;
    }
}
