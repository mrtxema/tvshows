package com.acme.tvshows.model;

public class ConnectionException extends ShowStoreException {

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
