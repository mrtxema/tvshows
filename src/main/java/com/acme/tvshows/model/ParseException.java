package com.acme.tvshows.model;

public class ParseException extends ShowStoreException {

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
