package com.acme.tvshows.video.api.v1;

import com.acme.tvshows.video.api.v1.model.NavigationRequest;
import com.acme.tvshows.video.model.ErrorType;
import com.acme.tvshows.video.model.VideoLinkException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class VideoController {

    public VideoController(VideoService videoService) {

        post("/video/v1/navigate", (req, res) -> {
            try {
                return videoService.navigate(fromJson(req.body(), NavigationRequest.class));
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

    private <T> T fromJson(String json, Class<T> objectClass) throws VideoLinkException {
        try {
            return new Gson().fromJson(json, objectClass);
        } catch (JsonSyntaxException e) {
            throw new VideoLinkException(ErrorType.INVALID_ARGUMENT, "Invalid JSON request", e.getCause());
        }
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return VideoController::toJson;
    }

}
