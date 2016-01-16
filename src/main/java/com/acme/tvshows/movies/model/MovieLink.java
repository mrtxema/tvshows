package com.acme.tvshows.movies.model;

import java.net.URL;

public interface MovieLink {
    String getId();

    String getServer();

    Language getLanguage();

    String getVideoQuality();

    String getAudioQuality();

    URL getUrl() throws MovieStoreException;
}
