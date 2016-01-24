package com.acme.tvshows.video.api.v1.model;

public class NavigationResponse {
    final String videoUrl;
    final NavigationAction navigationAction;

    public NavigationResponse(String videoUrl, NavigationAction navigationAction) {
        this.videoUrl = videoUrl;
        this.navigationAction = navigationAction;
    }
}
