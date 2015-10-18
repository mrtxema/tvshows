package com.acme.tvshows.api.v1;

import com.acme.tvshows.model.ShowStoreException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class TvShowController {

	void initializePort() {
		String port = new ProcessBuilder().environment().get("PORT");
        if (port != null) {
            setPort(Integer.parseInt(port));
        }
    }

	public TvShowController(final TvShowService tvShowService) {
		initializePort();

		get("/tvshows/v1", (req, res) -> tvShowService.getAllStores(), json());

		get("/tvshows/v1/:store", (req, res) -> {
			try {
				return tvShowService.findShows(req.params(":store"), req.queryParams("q"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v1/:store/:show", (req, res) -> {
			try {
				return tvShowService.getShowSeasons(req.params(":store"), req.params(":show"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v1/:store/:show/:season", (req, res) -> {
			try {
				return tvShowService.getSeasonEpisodes(req.params(":store"), req.params(":show"), req.params(":season"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v1/:store/:show/:season/:episode", (req, res) -> {
			try {
				return tvShowService.getEpisodeLinks(req.params(":store"), req.params(":show"), req.params(":season"), req.params(":episode"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v1/:store/:show/:season/:episode/:link", (req, res) -> {
			try {
				return tvShowService.getLinkUrl(req.params(":store"), req.params(":show"), req.params(":season"), req.params(":episode"), req.params(":link"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		after((req, res) -> {
			res.type("application/json; charset=utf-8");
		});

		exception(TvShowException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(Exception.class.cast(e.getCause()))));
		});
	}

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return TvShowController::toJson;
	}
}
