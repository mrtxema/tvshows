package com.acme.tvshows.api.filter;

public class TvShowAttributeValue {
	private final String attribute;
	private final String value;

	public TvShowAttributeValue(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getValue() {
		return value;
	}
}
