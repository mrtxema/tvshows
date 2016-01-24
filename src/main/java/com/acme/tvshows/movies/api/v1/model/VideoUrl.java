package com.acme.tvshows.movies.api.v1.model;

public class VideoUrl {
    final String url;
    final NavigationAction navigationAction;

    public VideoUrl(String url, com.acme.tvshows.movies.model.NavigationAction navigationAction) {
        this.url = url;
        this.navigationAction = navigationAction == null ? null : new NavigationAction(navigationAction.getUri(), navigationAction.getPostData());
    }
}
