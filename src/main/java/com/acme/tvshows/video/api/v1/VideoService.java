package com.acme.tvshows.video.api.v1;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.video.api.v1.model.NavigationAction;
import com.acme.tvshows.video.api.v1.model.NavigationRequest;
import com.acme.tvshows.video.api.v1.model.NavigationResponse;
import com.acme.tvshows.video.integration.VideoStoreManager;
import com.acme.tvshows.video.model.ErrorType;
import com.acme.tvshows.video.model.VideoLinkException;
import com.acme.tvshows.video.model.VideoStore;

public class VideoService {

    public NavigationResponse navigate(NavigationRequest navigationRequest) throws VideoLinkException {
        if (navigationRequest == null || navigationRequest.getNavigationAction() == null || navigationRequest.getServerResponse() == null) {
            throw new VideoLinkException(ErrorType.INVALID_ARGUMENT, "Invalid navigation request");
        }
        VideoStore store = BeanFactory.getInstance(VideoStoreManager.class).newStore(navigationRequest.getNavigationAction().getUri());
        return transformNavigationResponse(store.navigate(transformNavigationRequest(navigationRequest)));
    }

    private com.acme.tvshows.video.model.NavigationRequest transformNavigationRequest(NavigationRequest navigationRequest) {
        return new com.acme.tvshows.video.model.NavigationRequest(
                new com.acme.tvshows.video.model.NavigationAction(
                        navigationRequest.getNavigationAction().getUri(),
                        navigationRequest.getNavigationAction().getPostData()),
                navigationRequest.getServerResponse()
        );
    }

    private NavigationResponse transformNavigationResponse(com.acme.tvshows.video.model.NavigationResponse navigationResponse) {
        NavigationAction navigationAction = null;
        if (navigationResponse.getNavigationAction() != null) {
            navigationAction = new NavigationAction(
                    navigationResponse.getNavigationAction().getUri(),
                    navigationResponse.getNavigationAction().getPostData()
            );
        }
        return new NavigationResponse(navigationResponse.getVideoUrl(), navigationAction);
    }
}
