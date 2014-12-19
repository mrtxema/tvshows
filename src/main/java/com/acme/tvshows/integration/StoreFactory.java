package com.acme.tvshows.integration;

import com.acme.tvshows.model.ShowStoreException;
import com.acme.tvshows.model.Store;

public enum StoreFactory {
	SERIESYONKIS("seriesyonkis", com.acme.tvshows.integration.seriesyonkis.SeriesyonkisStore.class);

	private final String code;
	private final Class<? extends Store> clazz;

	StoreFactory(String code, Class<? extends Store> clazz) {
		this.code = code;
		this.clazz = clazz;
	}

	public String getCode() {
		return code;
	}

	public Store newStore() throws ShowStoreException {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ShowStoreException(String.format("Can't build %s store instance", name()), e);
		}
	}

	public static StoreFactory fromCode(String code) {
		for (StoreFactory factory : values()) {
			if (factory.getCode().equals(code)) {
				return factory;
			}
		}
		return null;
	}
}
