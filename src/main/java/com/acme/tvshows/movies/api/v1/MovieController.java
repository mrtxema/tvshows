package com.acme.tvshows.movies.api.v1;

import com.acme.tvshows.movies.model.MovieStoreException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;

public class MovieController {

    public MovieController(final MovieService movieService) {

        get("/movies/v1", (req, res) -> movieService.getAllStoreTypes(), json());

        get("/movies/v1/:store/login", (req, res) -> {
            try {
                return movieService.login(req.params(":store"), req.queryMap().toMap());
            } catch (MovieStoreException e) {
                throw new MovieApiException(e);
            }
        }, json());

        get("/movies/v1/:store/search", (req, res) -> {
            try {
                return movieService.searchMovies(req.params(":store"), req.queryParams("token"), req.queryParams("q"));
            } catch (MovieStoreException e) {
                throw new MovieApiException(e);
            }
        }, json());

        get("/movies/v1/:store/movie/:movie", (req, res) -> {
            try {
                return movieService.getMovieLinks(req.params(":store"), req.queryParams("token"), req.params(":movie"));
            } catch (MovieStoreException e) {
                throw new MovieApiException(e);
            }
        }, json());

        get("/movies/v1/:store/movie/:movie/:link", (req, res) -> {
            try {
                return movieService.getLinkUrl(req.params(":store"), req.queryParams("token"), req.params(":movie"), req.params(":link"));
            } catch (MovieStoreException e) {
                throw new MovieApiException(e);
            }
        }, json());

        after((req, res) -> {
            res.type("application/json; charset=utf-8");
        });

        exception(MovieApiException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(MovieStoreException.class.cast(e.getCause()))));
        });

    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return MovieController::toJson;
    }

}
