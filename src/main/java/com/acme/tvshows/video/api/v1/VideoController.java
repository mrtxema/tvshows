package com.acme.tvshows.video.api.v1;

import com.acme.tvshows.video.model.VideoLinkException;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;

public class VideoController {

    public VideoController(VideoService videoService) {

        get("/video/v1/link", (req, res) -> {
            try {
                return videoService.getVideoUrl(req.queryParams("url"));
            } catch (VideoLinkException e) {
                throw new VideoApiException(e);
            }
        }, json());

        after((req, res) -> {
            res.type("application/json; charset=utf-8");
        });

        exception(VideoApiException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(VideoLinkException.class.cast(e.getCause()))));
        });

    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return VideoController::toJson;
    }

}
