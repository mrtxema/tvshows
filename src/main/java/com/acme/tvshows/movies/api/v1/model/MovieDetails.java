package com.acme.tvshows.movies.api.v1.model;

import java.util.List;

public class MovieDetails {
    final List<MovieLink> links;

    public MovieDetails(List<MovieLink> links) {
        this.links = links;
    }
}
