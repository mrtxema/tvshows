package com.acme.tvshows.tv.integration;

import com.acme.tvshows.tv.model.Show;
import com.acme.tvshows.tv.model.ShowStoreException;
import com.acme.tvshows.tv.model.Store;

import java.util.List;
import java.util.Map;

public class CachedStore implements Store {
    private final Store store;
    private Show cachedShow;

    public CachedStore(Store store) {
        this.store = store;
    }

    @Override
    public String getName() {
        return store.getName();
    }

    @Override
    public List<Show> findShows(String showName) throws ShowStoreException {
        return store.findShows(showName);
    }

    @Override
    public Show getShow(String id) throws ShowStoreException {
        if (cachedShow == null || !cachedShow.getId().equals(id)) {
            cachedShow = store.getShow(id);
        }
        return cachedShow;
    }

    @Override
    public boolean login(Map<String, String> parameters) throws ShowStoreException {
        return store.login(parameters);
    }
}
