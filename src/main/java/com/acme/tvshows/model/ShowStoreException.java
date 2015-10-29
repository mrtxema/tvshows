package com.acme.tvshows.model;

public class ShowStoreException extends Exception {
	private final ErrorType errorType;

	public ShowStoreException(ErrorType errorType, String message) {
		super(message);
        this.errorType = errorType;
	}

	public ShowStoreException(ErrorType errorType, String message, Throwable cause) {
		super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
