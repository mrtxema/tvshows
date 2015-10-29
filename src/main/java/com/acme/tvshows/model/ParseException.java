package com.acme.tvshows.model;

public class ParseException extends ShowStoreException {

	public ParseException(String message, Throwable cause) {
		super(ErrorType.PARSE_ERROR, message, cause);
	}
}
