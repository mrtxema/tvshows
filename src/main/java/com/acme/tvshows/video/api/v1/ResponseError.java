package com.acme.tvshows.video.api.v1;

import com.acme.tvshows.video.model.VideoLinkException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ResponseError {
    private final int code;
    private final String message;
    private final String stackTrace;

    public ResponseError(VideoLinkException e) {
        this.code = e.getErrorType().getCode();
        this.message = e.getMessage();
        this.stackTrace = retrieveStackTrace(e);
    }

    private String retrieveStackTrace(Exception e) {
        StringWriter result = new StringWriter();
        e.printStackTrace(new PrintWriter(result));
        return result.toString();
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }
}
