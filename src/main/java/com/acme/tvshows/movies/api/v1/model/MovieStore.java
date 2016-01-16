package com.acme.tvshows.movies.api.v1.model;

import com.acme.tvshows.movies.integration.MovieStoreType;

import java.util.List;

public class MovieStore {
    final String code;
    final List<String> loginParameters;

    public MovieStore(MovieStoreType storeType) {
        code = storeType.getCode();
        loginParameters = storeType.getLoginParameters();
    }
}
