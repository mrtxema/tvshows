package com.acme.tvshows.integration;

import com.acme.tvshows.model.ShowStoreException;
import com.acme.tvshows.model.Store;

public enum StoreFactory {
	SERIESYONKIS(com.acme.tvshows.integration.seriesyonkis.SeriesyonkisStore.class);

	private final Class<? extends Store> clazz;

	StoreFactory(Class<? extends Store> clazz) {
		this.clazz = clazz;
	}

	public Store newStore() throws ShowStoreException {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ShowStoreException(String.format("Can't build %s store instance", name()), e);
		}
	}
}
