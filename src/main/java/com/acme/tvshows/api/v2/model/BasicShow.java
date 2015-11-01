package com.acme.tvshows.api.v2.model;

import com.acme.tvshows.model.Show;

public class BasicShow {
    final String id;
    final String name;

    public BasicShow(Show show) {
        id = show.getId();
        name = show.getName();
    }
}
