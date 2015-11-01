package com.acme.tvshows.api.v2.model;

import com.acme.tvshows.api.v2.TvShowService;
import com.acme.tvshows.model.Link;
import com.acme.tvshows.model.ShowStoreException;

public class BasicLink {
    final String id;
    final String server;
    final BasicLanguage language;

    public BasicLink(Link link) throws ShowStoreException {
        id = link.getId();
        server = link.getServer();
        language = new BasicLanguage(link.getLanguage());
    }
}
