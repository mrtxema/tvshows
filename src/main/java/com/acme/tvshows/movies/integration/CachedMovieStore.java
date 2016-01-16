package com.acme.tvshows.movies.integration;

import com.acme.tvshows.movies.model.Movie;
import com.acme.tvshows.movies.model.MovieStore;
import com.acme.tvshows.movies.model.MovieStoreException;

import java.util.List;
import java.util.Map;

public class CachedMovieStore implements MovieStore {
    private final MovieStore store;
    private Movie cachedMovie;

    public CachedMovieStore(MovieStore store) {
        this.store = store;
    }

    @Override
    public String getName() {
        return store.getName();
    }

    @Override
    public List<Movie> searchMovies(String showName) throws MovieStoreException {
        return store.searchMovies(showName);
    }

    @Override
    public Movie getMovie(String id) throws MovieStoreException {
        if (cachedMovie == null || !cachedMovie.getId().equals(id)) {
            cachedMovie = store.getMovie(id);
        }
        return cachedMovie;
    }

    @Override
    public boolean login(Map<String, String> parameters) throws MovieStoreException {
        return store.login(parameters);
    }
}
