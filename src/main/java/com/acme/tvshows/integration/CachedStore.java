package com.acme.tvshows.integration;

import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;

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
    public List<Show> findShows(String showName) throws ConnectionException, ParseException {
        return store.findShows(showName);
    }

    @Override
    public Show getShow(String id) throws ConnectionException, ParseException {
        if (cachedShow == null || !cachedShow.getId().equals(id)) {
            cachedShow = store.getShow(id);
        }
        return cachedShow;
    }

    @Override
    public boolean login(Map<String, String> parameters) throws ConnectionException {
        return store.login(parameters);
    }
}
