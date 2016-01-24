package com.acme.tvshows.movies.api.v1.model;

public class NavigationAction {
    final String uri;
    final String postData;

    public NavigationAction(String uri, String postData) {
        this.uri = uri;
        this.postData = postData;
    }
}
