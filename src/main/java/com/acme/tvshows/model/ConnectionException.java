package com.acme.tvshows.model;

public class ConnectionException extends ShowStoreException {

	public ConnectionException(String message, Throwable cause) {
		super(ErrorType.CONNECTION_ERROR, message, cause);
	}
}
