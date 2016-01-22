package com.acme.tvshows.video.api.v1;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.video.integration.VideoStoreManager;
import com.acme.tvshows.video.model.VideoLinkException;
import com.acme.tvshows.video.model.VideoStore;

public class VideoService {
    public VideoLink getVideoUrl(String link) throws VideoLinkException {
        VideoStore store = BeanFactory.getInstance(VideoStoreManager.class).newStore(link);
        String url = store.getVideoUrl(link);
        return url == null ? null : new VideoLink(url);
    }
}
