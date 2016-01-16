package com.acme.tvshows.tv.api.v2.model;

import com.acme.tvshows.tv.model.Episode;

public class BasicEpisode {
    final int number;
    final String title;

    public BasicEpisode(Episode episode) {
        number = episode.getNumber();
        title = episode.getTitle();
    }
}
