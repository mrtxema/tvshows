package com.acme.tvshows.api;

import com.acme.tvshows.integration.StoreFactory;
import java.util.EnumSet;
import java.util.Set;

public class TvShowService {

	public Set<StoreFactory> getAllStores() {
		return EnumSet.allOf(StoreFactory.class);
	}
}
