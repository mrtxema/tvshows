package com.acme.tvshows.api.v2;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ResponseError {

	private String message;
	private String stackTrace;

	public ResponseError(String message, String... args) {
		this.message = String.format(message, args);
	}

	public ResponseError(Exception e) {
		this.message = e.getMessage();
		this.stackTrace = retrieveStackTrace(e);
	}

	private String retrieveStackTrace(Exception e) {
		StringWriter result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
		return result.toString();
	}

	public String getMessage() {
		return this.message;
	}

	public String getStackTrace() {
		return this.stackTrace;
	}
}
