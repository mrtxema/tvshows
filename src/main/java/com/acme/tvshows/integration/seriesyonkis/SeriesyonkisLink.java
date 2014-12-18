package com.acme.tvshows.integration.seriesyonkis;

import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import java.net.MalformedURLException;
import java.net.URL;
import com.acme.tvshows.model.Language;
import com.acme.tvshows.model.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisLink implements Link {
	private final String urlSelect;
	private final String urlAttr;
	private final String realUrlSelect;
	private final String realUrlAttr;
	private final String serverSelect;
	private final String serverAttr;
	private final String languageSelect;

	private final Element element;
	private URL url;

	public SeriesyonkisLink(Element element) {
		SeriesyonkisConfiguration config = BeanFactory.getInstance(SeriesyonkisConfiguration.class);
		this.urlSelect = config.getLinkUrlSelect();
		this.urlAttr = config.getLinkUrlAttr();
		this.realUrlSelect = config.getLinkRealUrlSelect();
		this.realUrlAttr = config.getLinkRealUrlAttr();
		this.serverSelect = config.getLinkServerSelect();
		this.serverAttr = config.getLinkServerAttr();
		this.languageSelect = config.getLinkLanguageSelect();
		this.element = element;
	}

	public URL getUrl() throws ConnectionException, ParseException {
		if (url == null) {
			buildRealUrl(element.select(urlSelect).first().attr(urlAttr));
		}
		return url;
	}

	public String getServer() {
		return element.select(serverSelect).first().attr(serverAttr);
	}

	public Language getLanguage() {
		return new SeriesyonkisLanguage(element.select(languageSelect).first());
	}

	private synchronized void buildRealUrl(String initialUrl) throws ConnectionException, ParseException {
		if (url == null) {
			Document document = ParseHelper.getInstance().parseUrl(initialUrl);
			String urlText = document.select(realUrlSelect).first().attr(realUrlAttr);
			try {
				url = new URL(urlText);
			} catch (MalformedURLException e) {
				throw new ParseException(String.format("Invalid URL: %s", urlText), e);
			}
		}
	}
}
