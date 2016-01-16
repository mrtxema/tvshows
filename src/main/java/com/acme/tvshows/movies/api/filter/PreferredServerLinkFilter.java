package com.acme.tvshows.movies.api.filter;

import com.acme.tvshows.movies.model.MovieLink;
import com.acme.tvshows.movies.model.MovieStoreException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PreferredServerLinkFilter implements MovieFilter<MovieLink> {
    private final Collection<String> preferredServers;

    public PreferredServerLinkFilter(List<String> preferredServers) {
        this.preferredServers = Collections.unmodifiableCollection(toLower(preferredServers));
    }

    private List<String> toLower(List<String> strings) {
        List<String> result = new ArrayList<>();
        for (String s : strings) {
            result.add(s.toLowerCase());
        }
        return result;
    }

    @Override
    public Collection<MovieLink> filter(MovieLink link) throws MovieStoreException {
        for (String preferredServer : preferredServers) {
            if (link.getServer().toLowerCase().contains(preferredServer)) {
                return Collections.singletonList(link);
            }
        }
        return Collections.emptyList();
    }
}
