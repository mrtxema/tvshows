package com.acme.tvshows.tv.integration;

import com.acme.tvshows.tv.model.ErrorType;
import com.acme.tvshows.tv.model.SessionExpirationException;
import com.acme.tvshows.tv.model.ShowStoreException;
import com.acme.tvshows.tv.model.Store;
import com.acme.tvshows.util.LRUCache;
import com.acme.tvshows.util.Singleton;

import java.util.Map;
import java.util.UUID;

@Singleton
public class StoreManager {
    private static final long CACHE_EXPIRATION_MILLIS = 1800000;
    private final LRUCache<String,Store> cache = new LRUCache<>(CACHE_EXPIRATION_MILLIS);

    private StoreType getStoreType(String store) throws ShowStoreException {
        StoreType factory = StoreType.fromCode(store);
        if (factory == null) {
            throw new ShowStoreException(ErrorType.INVALID_ARGUMENT, "Invalid store name: " + store);
        }
        return factory;
    }

    public Store getStore(String store, String token) throws ShowStoreException {
        Store result = cache.get(token);
        if (result == null) {
            throw new SessionExpirationException(String.format("Token '%s' has expired", token));
        }
        return result;
    }

    public String newStore(String store, Map<String, String> loginParameters) throws ShowStoreException {
        Store storeInstance = null;
        try {
            storeInstance = getStoreType(store).getStoreClass().newInstance();
        } catch (InstantiationException | IllegalAccessException  e) {
            throw new ShowStoreException(ErrorType.INTERNAL_ERROR, String.format("Can't build %s store instance", store), e);
        }
        if (storeInstance.login(loginParameters)) {
            final String token = UUID.randomUUID().toString();
            cache.put(token, new CachedStore(storeInstance));
            return token;
        } else {
            throw new ShowStoreException(ErrorType.WRONG_CREDENTIALS, "Invalid login credentials");
        }
    }
}
