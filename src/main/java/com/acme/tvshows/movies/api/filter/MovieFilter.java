package com.acme.tvshows.movies.api.filter;

import com.acme.tvshows.movies.model.MovieStoreException;

import java.util.Collection;

public interface MovieFilter<T> {
    Collection<T> filter(T bean) throws MovieStoreException;
}
