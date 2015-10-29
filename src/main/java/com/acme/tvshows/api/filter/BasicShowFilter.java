package com.acme.tvshows.api.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.acme.tvshows.model.ErrorType;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.ShowStoreException;

public class BasicShowFilter implements TvShowFilter<Show> {
	private final List<TvShowAttributeValue> conditions;
	private final List<TvShowAttributeValue> updates;

	public BasicShowFilter(List<TvShowAttributeValue> conditions, List<TvShowAttributeValue> updates) {
		this.conditions = conditions;
		this.updates = updates;
	}

	public Collection<Show> filter(Show bean) throws ShowStoreException {
		Show result = matches(bean) ? update(bean) : bean;
		return Collections.singleton(result);
	}

	private boolean matches(Show bean) throws ShowStoreException {
		for (TvShowAttributeValue condition : conditions) {
			if (!matches(condition, bean)) {
				return false;
			}
		}
		return true;
	}

	private boolean matches(TvShowAttributeValue condition, Show bean) throws ShowStoreException {
		return condition.getValue().equals(getAttributeValue(bean, condition.getAttribute()));
	}

	private String getAttributeValue(Show bean, String attribute) throws ShowStoreException {
		if (attribute.equals("id")) {
			return bean.getId();
		} else if (attribute.equals("name")) {
			return bean.getName();
		} else {
			throw new ShowStoreException(ErrorType.INTERNAL_ERROR, "Unknown attribute: " + attribute);
		}
	}

	private Show update(Show bean) {
		return new ShowWrapper(bean, updates);
	}
}
