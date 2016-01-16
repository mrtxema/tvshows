package com.acme.tvshows.tv.api.filter;

import java.util.Collection;
import com.acme.tvshows.tv.model.ShowStoreException;

public interface TvShowFilter<T> {

	Collection<T> filter(T bean) throws ShowStoreException;
}
