package com.acme.tvshows.video.model;

public interface VideoStore {
    String getName();

    String getVideoUrl(String link) throws VideoLinkException;
}
