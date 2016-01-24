package com.acme.tvshows.video.api.v1.model;

public class NavigationAction {
    final String uri;
    final String postData;

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
