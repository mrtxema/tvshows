package com.acme.tvshows.movies.api.v1;

import com.acme.tvshows.movies.model.MovieStoreException;

import java.util.List;

public class MovieFilterManager {
    <T> List<T> filter(Class<T> clazz, List<T> beans) throws MovieStoreException {
        return beans;
    }
}
