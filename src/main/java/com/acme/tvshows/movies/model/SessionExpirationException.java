package com.acme.tvshows.movies.model;

public class SessionExpirationException extends MovieStoreException {
    public SessionExpirationException(String message) {
        super(ErrorType.SESSION_EXPIRED, message);
    }
}
