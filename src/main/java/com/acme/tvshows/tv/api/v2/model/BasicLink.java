package com.acme.tvshows.tv.api.v2.model;

import com.acme.tvshows.tv.model.Link;
import com.acme.tvshows.tv.model.ShowStoreException;

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
