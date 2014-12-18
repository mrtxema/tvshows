package com.acme.tvshows.model;

public class ShowStoreException extends Exception {

	public ShowStoreException(String message) {
		super(message);
	}

	public ShowStoreException(String message, Throwable cause) {
		super(message, cause);
	}
}
