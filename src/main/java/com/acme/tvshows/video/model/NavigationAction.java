package com.acme.tvshows.video.model;

public class NavigationAction {
    private final String uri;
    private final String postData;

    public NavigationAction(String uri, String postData) {
        this.uri = uri;
        this.postData = postData;
    }

    public String getUri() {
        return uri;
    }

    public String getPostData() {
        return postData;
    }
}
