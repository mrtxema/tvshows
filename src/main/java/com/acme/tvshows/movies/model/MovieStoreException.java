package com.acme.tvshows.movies.model;

public class MovieStoreException extends Exception {
    private final ErrorType errorType;

    public MovieStoreException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public MovieStoreException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
