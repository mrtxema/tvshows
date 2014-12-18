package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.model.Language;
import org.jsoup.nodes.Element;

public class SeriesyonkisLanguage implements Language {
	private final String codeAttr;
	private final Element element;

	public SeriesyonkisLanguage(Element element) {
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		this.codeAttr = config.getLanguageCodeAttr();
		this.element = element;
	}

	public String getCode() {
		String classes = element.attr(codeAttr);
		return classes.substring(classes.lastIndexOf(' ') + 1);
	}

	public String getName() {
		return element.ownText();
	}
}
