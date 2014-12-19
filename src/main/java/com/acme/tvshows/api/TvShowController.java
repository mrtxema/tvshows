package com.acme.tvshows.api;

import com.acme.tvshows.model.ShowStoreException;
import java.util.function.Supplier;
import java.util.concurrent.Callable;
import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Route;
import static spark.Spark.*;

public class TvShowController {

	public TvShowController(final TvShowService tvShowService) {

		get(
			"/tvshows",
			(req, res) -> tvShowService.getAllStores(),
			json()
		);

		get("/tvshows/:store", (req, res) -> {
			try {
				return tvShowService.findShows(req.params(":store"), req.queryParams("q"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/:store/:show", (req, res) -> {
			try {
				return tvShowService.getShowSeasons(req.params(":store"), req.params(":show"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/:store/:show/:season", (req, res) -> {
			try {
				return tvShowService.getSeasonEpisodes(req.params(":store"), req.params(":show"), req.params(":season"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/:store/:show/:season/:episode", (req, res) -> {
			try {
				return tvShowService.getEpisodeLinks(req.params(":store"), req.params(":show"), req.params(":season"), req.params(":episode"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		after((req, res) -> {
			res.type("application/json");
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
