package com.acme.tvshows.integration;

import com.acme.tvshows.integration.seriesyonkis.SeriesyonkisStore;
import com.acme.tvshows.model.Store;
import java.util.Collections;
import java.util.List;

public enum StoreType {
	SERIESYONKIS("seriesyonkis", SeriesyonkisStore.class, Collections.<String>emptyList());

	private final String code;
	private final Class<? extends Store> clazz;
	private final List<String> loginParameters;

	StoreType(String code, Class<? extends Store> clazz, List<String> loginParameters) {
		this.code = code;
		this.clazz = clazz;
		this.loginParameters = loginParameters;
	}

	public String getCode() {
		return code;
	}

	public Class<? extends Store> getStoreClass() {
		return clazz;
	}

    public List<String> getLoginParameters() {
        return loginParameters;
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
