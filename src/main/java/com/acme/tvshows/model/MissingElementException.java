package com.acme.tvshows.model;

public class MissingElementException extends ShowStoreException {

	public MissingElementException(String message) {
		super(message);
	}

	public MissingElementException(String message, Throwable cause) {
		super(message, cause);
	}
}
