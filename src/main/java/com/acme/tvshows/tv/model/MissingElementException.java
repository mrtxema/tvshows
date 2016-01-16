package com.acme.tvshows.tv.model;

public class MissingElementException extends ShowStoreException {

	public MissingElementException(String message) {
		super(ErrorType.MISSING_ELEMENT, message);
	}
}
