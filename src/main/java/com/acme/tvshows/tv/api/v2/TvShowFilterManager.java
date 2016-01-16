package com.acme.tvshows.tv.api.v2;

import com.acme.tvshows.tv.api.filter.BasicShowFilter;
import com.acme.tvshows.tv.api.filter.PreferredServerLinkFilter;
import com.acme.tvshows.tv.api.filter.TvShowAttributeValue;
import com.acme.tvshows.tv.api.filter.TvShowFilter;
import com.acme.tvshows.tv.model.Link;
import com.acme.tvshows.tv.model.Show;
import com.acme.tvshows.tv.model.ShowStoreException;

import java.util.*;

public class TvShowFilterManager {
    private final Map<Class<?>, List<TvShowFilter<?>>> filters;

    TvShowFilterManager() {
        filters = retrieveFilters();
    }

    private Map<Class<?>, List<TvShowFilter<?>>> retrieveFilters() {
        Map<Class<?>, List<TvShowFilter<?>>> result = new HashMap<Class<?>, List<TvShowFilter<?>>>();
        result.put(Show.class, Collections.singletonList(
                new BasicShowFilter(Collections.singletonList(new TvShowAttributeValue("id", "big_bang_theory")), Collections.singletonList(new TvShowAttributeValue("id", "the-big-bang-theory")))
        ));
        result.put(Link.class, Collections.singletonList(
                new PreferredServerLinkFilter(Collections.singletonList("streamcloud"))
        ));
        return result;
    }

    <T> List<T> filter(Class<T> clazz, List<T> beans) throws ShowStoreException {
        if (!filters.containsKey(clazz) || beans.isEmpty()) {
            return beans;
        }
        List<T> result = beans;
        for (TvShowFilter<?> filterItem : filters.get(clazz)) {
            TvShowFilter<T> filter = (TvShowFilter<T>) filterItem;
            List<T> filtered = new ArrayList<T>();
            for (T bean : result) {
                filtered.addAll(filter.filter(bean));
            }
            result = filtered;
        }
        return result.isEmpty() ? beans : result;
    }
}
