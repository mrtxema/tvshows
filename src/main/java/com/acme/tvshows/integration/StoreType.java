package com.acme.tvshows.integration;

import com.acme.tvshows.model.ShowStoreException;
import com.acme.tvshows.model.Store;

public enum StoreType {
	SERIESYONKIS("seriesyonkis", com.acme.tvshows.integration.seriesyonkis.SeriesyonkisStore.class);

	private final String code;
	private final Class<? extends Store> clazz;

	StoreType(String code, Class<? extends Store> clazz) {
		this.code = code;
		this.clazz = clazz;
	}

	public String getCode() {
		return code;
	}

	public Class<? extends Store> getStoreClass() {
		return clazz;
	}

	public static StoreType fromCode(String code) {
		for (StoreType factory : values()) {
			if (factory.getCode().equals(code)) {
				return factory;
			}
		}
		return null;
	}
}
