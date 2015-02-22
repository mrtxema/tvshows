package com.acme.tvshows.api.filter;

import java.util.Collection;
import com.acme.tvshows.model.ShowStoreException;

public interface TvShowFilter<T> {

	Collection<T> filter(T bean) throws ShowStoreException;
}
