package com.acme.tvshows.video.integration;

import com.acme.tvshows.util.Singleton;
import com.acme.tvshows.video.integration.streamcloud.StreamcloudVideoStore;
import com.acme.tvshows.video.model.ErrorType;
import com.acme.tvshows.video.model.VideoLinkException;
import com.acme.tvshows.video.model.VideoStore;

import java.net.MalformedURLException;
import java.net.URL;

@Singleton
public class VideoStoreManager {

    public VideoStore newStore(String link) throws VideoLinkException {
        try {
            return newStoreFromHost(new URL(link).getHost().toLowerCase());
        } catch (MalformedURLException e) {
            throw new VideoLinkException(ErrorType.INVALID_ARGUMENT, "Invalid URL", e);
        }
    }

    private VideoStore newStoreFromHost(String host) throws VideoLinkException {
        if (host.endsWith("streamcloud.eu")) {
            return new StreamcloudVideoStore();
        }
        throw new VideoLinkException(ErrorType.INVALID_ARGUMENT, "Server not supported: " + host);
    }
}
