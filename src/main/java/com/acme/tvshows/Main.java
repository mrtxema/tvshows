package com.acme.tvshows;

import com.acme.tvshows.api.TvShowController;
import com.acme.tvshows.api.TvShowService;

public class Main {
	public static void main(String[] args) {
		new TvShowController(new TvShowService());
	}
}
