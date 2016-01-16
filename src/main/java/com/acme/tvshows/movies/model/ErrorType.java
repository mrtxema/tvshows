package com.acme.tvshows.movies.model;

public enum ErrorType {
    INVALID_ARGUMENT(1),
    INTERNAL_ERROR(2),
    MISSING_ELEMENT(3),
    CONNECTION_ERROR(4),
    PARSE_ERROR(5),
    SESSION_EXPIRED(6),
    WRONG_CREDENTIALS(7);

    private final int code;

    ErrorType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
