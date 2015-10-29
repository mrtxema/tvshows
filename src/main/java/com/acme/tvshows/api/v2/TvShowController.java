package com.acme.tvshows.api.v2;

import com.acme.tvshows.model.ShowStoreException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class TvShowController {

	public TvShowController(final TvShowService tvShowService) {

		get("/tvshows/v2", (req, res) -> tvShowService.getAllStoreTypes(), json());

		get("/tvshows/v2/:store/login", (req, res) -> {
			try {
				return tvShowService.login(req.params(":store"), req.queryMap().toMap());
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v2/:store/searchshow", (req, res) -> {
			try {
				return tvShowService.findShows(req.params(":store"), req.queryParams("token"), req.queryParams("q"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v2/:store/show/:show", (req, res) -> {
			try {
				return tvShowService.getShowSeasons(req.params(":store"), req.queryParams("token"), req.params(":show"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v2/:store/show/:show/:season", (req, res) -> {
			try {
				return tvShowService.getSeasonEpisodes(req.params(":store"), req.queryParams("token"), req.params(":show"), req.params(":season"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v2/:store/show/:show/:season/:episode", (req, res) -> {
			try {
				return tvShowService.getEpisodeLinks(req.params(":store"), req.queryParams("token"), req.params(":show"), req.params(":season"), req.params(":episode"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		get("/tvshows/v2/:store/show/:show/:season/:episode/:link", (req, res) -> {
			try {
				return tvShowService.getLinkUrl(req.params(":store"), req.queryParams("token"), req.params(":show"), req.params(":season"), req.params(":episode"), req.params(":link"));
			} catch (ShowStoreException e) {
				throw new TvShowException(e);
			}
		}, json());

		after((req, res) -> {
			res.type("application/json; charset=utf-8");
		});

		exception(TvShowException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(ShowStoreException.class.cast(e.getCause()))));
		});
	}

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return TvShowController::toJson;
	}
}
