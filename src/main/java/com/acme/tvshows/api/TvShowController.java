package com.acme.tvshows.api;

import com.google.gson.Gson;
import spark.ResponseTransformer;
import static spark.Spark.*;

public class TvShowController {

	public TvShowController(final TvShowService tvShowService) {

		get("/stores", (req, res) -> tvShowService.getAllStores(), json());

		after((req, res) -> {
			res.type("application/json");
		});

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(e)));
		});
	}

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return TvShowController::toJson;
	}
}
