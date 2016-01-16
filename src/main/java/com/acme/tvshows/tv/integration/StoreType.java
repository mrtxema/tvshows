package com.acme.tvshows.tv.integration;

import com.acme.tvshows.tv.integration.pordede.PordedeStore;
import com.acme.tvshows.tv.integration.seriesyonkis.SeriesyonkisStore;
import com.acme.tvshows.tv.model.Store;

import java.util.*;

public enum StoreType {
	SERIESYONKIS("seriesyonkis", SeriesyonkisStore.class, 1, Collections.<String>emptyList()),
    PORDEDE("pordede", PordedeStore.class, 2, Arrays.asList("username", "password"));

	private final String code;
	private final Class<? extends Store> clazz;
	private final int fromVersion;
	private final List<String> loginParameters;

	StoreType(String code, Class<? extends Store> clazz, int fromVersion, List<String> loginParameters) {
		this.code = code;
		this.clazz = clazz;
        this.fromVersion = fromVersion;
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

    public static Set<StoreType> getValues(int version) {
        Set<StoreType> result = EnumSet.noneOf(StoreType.class);
        for (StoreType storeType : values()) {
            if (version >= storeType.fromVersion) {
                result.add(storeType);
            }
        }
        return result;
    }
}
