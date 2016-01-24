package com.acme.tvshows.video.model;

public class NavigationRequest {
    private final NavigationAction navigationAction;
    private final String serverResponse;

    public NavigationRequest(NavigationAction navigationAction, String serverResponse) {
        this.navigationAction = navigationAction;
        this.serverResponse = serverResponse;
    }

    public NavigationAction getNavigationAction() {
        return navigationAction;
    }

    public String getServerResponse() {
        return serverResponse;
    }
}
