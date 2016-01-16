package com.acme.tvshows.movies.integration;

import com.acme.tvshows.movies.integration.pordede.PordedeMovieStore;
import com.acme.tvshows.movies.model.MovieStore;

import java.util.*;

public enum MovieStoreType {
    PORDEDE("pordede", PordedeMovieStore.class, 1, Arrays.asList("username", "password"));

    private final String code;
    private final Class<? extends MovieStore> clazz;
    private final int fromVersion;
    private final List<String> loginParameters;

    MovieStoreType(String code, Class<? extends MovieStore> clazz, int fromVersion, List<String> loginParameters) {
        this.code = code;
        this.clazz = clazz;
        this.fromVersion = fromVersion;
        this.loginParameters = loginParameters;
    }

    public String getCode() {
        return code;
    }

    public Class<? extends MovieStore> getStoreClass() {
        return clazz;
    }

    public List<String> getLoginParameters() {
        return loginParameters;
    }

    public static MovieStoreType fromCode(String code) {
        for (MovieStoreType factory : values()) {
            if (factory.getCode().equals(code)) {
                return factory;
            }
        }
        return null;
    }

    public static Set<MovieStoreType> getValues(int version) {
        Set<MovieStoreType> result = EnumSet.noneOf(MovieStoreType.class);
        for (MovieStoreType storeType : values()) {
            if (version >= storeType.fromVersion) {
                result.add(storeType);
            }
        }
        return result;
    }

}
