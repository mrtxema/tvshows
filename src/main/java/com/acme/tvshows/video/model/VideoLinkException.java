package com.acme.tvshows.video.model;

public class VideoLinkException extends Exception {
    private final ErrorType errorType;

    public VideoLinkException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public VideoLinkException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
