package com.acme.tvshows;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.integration.StoreFactory;
import com.acme.tvshows.integration.seriesyonkis.SeriesyonkisConfiguration;
import com.acme.tvshows.model.Link;
import com.acme.tvshows.model.ShowStoreException;
import com.acme.tvshows.api.TvShowController;
import com.acme.tvshows.api.TvShowService;

public class Main {

	public void test1() {
		try {
			Link link = StoreFactory.SERIESYONKIS.newStore().getShow("breaking_bad").getSeason(4).getEpisode(1).getLinks().get(0);
			System.out.printf("%s - %s(%s) - %s%n", link.getServer(), link.getLanguage().getName(), link.getLanguage().getCode(), link.getUrl());
		} catch (ShowStoreException e) {
			e.printStackTrace();
		}
	}

	public void test2() {
		try {
			Link link = StoreFactory.SERIESYONKIS.newStore().findShows("break").get(1).getSeason(2).getEpisode(3).getLinks().get(4);
			System.out.printf("%s - %s(%s) - %s%n", link.getServer(), link.getLanguage().getName(), link.getLanguage().getCode(), link.getUrl());
		} catch (ShowStoreException e) {
			e.printStackTrace();
		}
	}

	public void test3() {
		BeanFactory.getInstance(java.util.ArrayList.class);
		BeanFactory.getInstance(java.util.ArrayList.class);
		BeanFactory.getInstance(java.util.ArrayList.class);

		BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		SeriesyonkisConfiguration cfg = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		System.out.println(cfg.getStoreSearchUrl());
		System.out.println(cfg.getStoreName());
	}

	public void run() {
		test1();
		test2();
		test3();
	}

	public static void main(String[] args) {
		//new Main().run();
		new TvShowController(new TvShowService());
	}
}
