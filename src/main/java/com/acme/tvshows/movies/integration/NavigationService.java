package com.acme.tvshows.movies.integration;

import com.acme.tvshows.movies.model.MovieLink;
import com.acme.tvshows.movies.model.MovieStoreException;
import com.acme.tvshows.movies.model.NavigationAction;
import com.acme.tvshows.util.Singleton;

@Singleton
public class NavigationService {
    private static final String STREAMCLOUD_HOST = "streamcloud.eu";
    private static final String STREAMCLOUD_POST_DATA_PATTERN = "op=download1&usr_login=&id=%s&fname=%s&referer=&hash=&imhuman=Watch+video+now";

    public NavigationAction getNavigationAction(MovieLink link) throws MovieStoreException {
        String host = link.getUrl().getHost().toLowerCase();
        if (host.endsWith(STREAMCLOUD_HOST)) {
            return buildStreamcloudNavigationAction(link);
        }
        return null;
    }

    private NavigationAction buildStreamcloudNavigationAction(MovieLink link) throws MovieStoreException {
        String[] pathComponents = link.getUrl().getPath().split("/");
        int idx = pathComponents[2].lastIndexOf('.');
        if (idx != -1) {
            String postData = String.format(STREAMCLOUD_POST_DATA_PATTERN, pathComponents[1], pathComponents[2].substring(0, idx));
            return new NavigationAction(link.getUrl().toString(), postData);
        }
        return null;
    }
}
