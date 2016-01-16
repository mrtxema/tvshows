package com.acme.tvshows.movies.model;

import java.util.List;
import java.util.Map;

public interface MovieStore {

    String getName();

    List<Movie> searchMovies(String showName) throws MovieStoreException;

    Movie getMovie(String id) throws MovieStoreException;

    boolean login(Map<String, String> parameters) throws MovieStoreException;
}
