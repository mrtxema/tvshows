package com.acme.tvshows.movies.integration;

import com.acme.tvshows.movies.model.ErrorType;
import com.acme.tvshows.movies.model.MovieStore;
import com.acme.tvshows.movies.model.MovieStoreException;
import com.acme.tvshows.movies.model.SessionExpirationException;
import com.acme.tvshows.util.LRUCache;
import com.acme.tvshows.util.Singleton;

import java.util.Map;
import java.util.UUID;

@Singleton
public class MovieStoreManager {
    private static final long CACHE_EXPIRATION_MILLIS = 1800000;
    private final LRUCache<String,MovieStore> cache = new LRUCache<>(CACHE_EXPIRATION_MILLIS);

    private MovieStoreType getStoreType(String store) throws MovieStoreException {
        MovieStoreType factory = MovieStoreType.fromCode(store);
        if (factory == null) {
            throw new MovieStoreException(ErrorType.INVALID_ARGUMENT, "Invalid store name: " + store);
        }
        return factory;
    }

    public MovieStore getStore(String store, String token) throws MovieStoreException {
        MovieStore result = cache.get(token);
        if (result == null) {
            throw new SessionExpirationException(String.format("Token '%s' has expired", token));
        }
        return result;
    }

    public String newStore(String store, Map<String, String> loginParameters) throws MovieStoreException {
        MovieStore storeInstance = null;
        try {
            storeInstance = getStoreType(store).getStoreClass().newInstance();
        } catch (InstantiationException | IllegalAccessException  e) {
            throw new MovieStoreException(ErrorType.INTERNAL_ERROR, String.format("Can't build %s store instance", store), e);
        }
        if (storeInstance.login(loginParameters)) {
            final String token = UUID.randomUUID().toString();
            cache.put(token, new CachedMovieStore(storeInstance));
            return token;
        } else {
            throw new MovieStoreException(ErrorType.WRONG_CREDENTIALS, "Invalid login credentials");
        }
    }
}
