package com.acme.tvshows.movies.api.v1;

import com.acme.tvshows.movies.api.filter.MovieFilter;
import com.acme.tvshows.movies.api.filter.PreferredServerLinkFilter;
import com.acme.tvshows.movies.model.MovieLink;
import com.acme.tvshows.movies.model.MovieStoreException;

import java.util.*;

public class MovieFilterManager {
    private final Map<Class<?>, List<MovieFilter<?>>> filters;

    MovieFilterManager() {
        filters = retrieveFilters();
    }

    private Map<Class<?>, List<MovieFilter<?>>> retrieveFilters() {
        Map<Class<?>, List<MovieFilter<?>>> result = new HashMap<>();
        result.put(MovieLink.class, Collections.singletonList(
                new PreferredServerLinkFilter(Collections.singletonList("streamcloud"))
        ));
        return result;
    }

    <T> List<T> filter(Class<T> clazz, List<T> beans) throws MovieStoreException {
        if (!filters.containsKey(clazz) || beans.isEmpty()) {
            return beans;
        }
        List<T> result = beans;
        for (MovieFilter<?> filterItem : filters.get(clazz)) {
            MovieFilter<T> filter = (MovieFilter<T>) filterItem;
            List<T> filtered = new ArrayList<>();
            for (T bean : result) {
                filtered.addAll(filter.filter(bean));
            }
            result = filtered;
        }
        return result.isEmpty() ? beans : result;
    }
}
