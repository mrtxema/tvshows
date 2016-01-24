package com.acme.tvshows.video.model;

public interface VideoStore {
    String getName();

    NavigationResponse navigate(NavigationRequest request) throws VideoLinkException;
}
