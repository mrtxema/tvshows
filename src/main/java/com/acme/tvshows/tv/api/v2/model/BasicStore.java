package com.acme.tvshows.tv.api.v2.model;

import com.acme.tvshows.tv.integration.StoreType;

import java.util.List;

public class BasicStore {
    final String code;
    final List<String> loginParameters;

    public BasicStore(StoreType storeType) {
        code = storeType.getCode();
        loginParameters = storeType.getLoginParameters();
    }
}
