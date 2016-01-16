package com.acme.tvshows.movies.model;

public class MissingElementException extends MovieStoreException {
    public MissingElementException(String message) {
        super(ErrorType.MISSING_ELEMENT, message);
    }
}
