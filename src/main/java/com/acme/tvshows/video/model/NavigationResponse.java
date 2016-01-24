package com.acme.tvshows.video.model;

public class NavigationResponse {
    private final String videoUrl;
    private final NavigationAction navigationAction;

    public NavigationResponse(String videoUrl) {
        this.videoUrl = videoUrl;
        this.navigationAction = null;
    }

    public NavigationResponse(NavigationAction navigationAction) {
        this.videoUrl = null;
        this.navigationAction = navigationAction;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public NavigationAction getNavigationAction() {
        return navigationAction;
    }
}
