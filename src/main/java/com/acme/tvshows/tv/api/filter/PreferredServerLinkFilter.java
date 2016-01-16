package com.acme.tvshows.tv.api.filter;

import com.acme.tvshows.tv.model.Link;
import com.acme.tvshows.tv.model.ShowStoreException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PreferredServerLinkFilter implements TvShowFilter<Link> {
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
    public Collection<Link> filter(Link link) throws ShowStoreException {
        for (String preferredServer : preferredServers) {
            if (link.getServer().toLowerCase().contains(preferredServer)) {
                return Collections.singletonList(link);
            }
        }
        return Collections.emptyList();
    }
}
