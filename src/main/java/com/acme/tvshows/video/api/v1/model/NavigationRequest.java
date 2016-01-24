package com.acme.tvshows.video.api.v1.model;

public class NavigationRequest {
    final NavigationAction navigationAction;
    final String serverResponse;

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
