package com.acme.tvshows.model;

public class SessionExpirationException extends ShowStoreException {

    public SessionExpirationException(String message) {
        super(ErrorType.SESSION_EXPIRED, message);
    }
}
