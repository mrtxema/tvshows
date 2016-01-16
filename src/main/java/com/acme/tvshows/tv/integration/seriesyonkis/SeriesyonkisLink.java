package com.acme.tvshows.tv.integration.seriesyonkis;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.tv.model.ErrorType;
import com.acme.tvshows.tv.model.ShowStoreException;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import java.net.MalformedURLException;
import java.net.URL;
import com.acme.tvshows.tv.model.Language;
import com.acme.tvshows.tv.model.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeriesyonkisLink implements Link {
	private final ParseHelper parseHelper;
	private final String urlSelect;
	private final String urlAttr;
	private final String realUrlSelect;
	private final String realUrlAttr;
	private final String serverSelect;
	private final String serverAttr;
	private final String languageSelect;

	private final Element element;
	private URL url;

	public SeriesyonkisLink(ParseHelper parseHelper, Element element) {
        this.parseHelper = parseHelper;
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

	private String getInitialUrl() {
		return element.select(urlSelect).first().attr(urlAttr);
	}

	public String getId() {
		String initialUrl = getInitialUrl();
		return initialUrl.substring(initialUrl.lastIndexOf('/') + 1);
	}

	public URL getUrl() throws ShowStoreException {
		if (url == null) {
			buildRealUrl(getInitialUrl());
		}
		return url;
	}

	public String getServer() {
		return element.select(serverSelect).first().attr(serverAttr);
	}

	public Language getLanguage() {
		return new SeriesyonkisLanguage(element.select(languageSelect).first());
	}

	private synchronized void buildRealUrl(String initialUrl) throws ShowStoreException {
		if (url == null) {
			try {
				Document document = parseHelper.parseUrl(initialUrl);
				String urlText = document.select(realUrlSelect).first().attr(realUrlAttr);
				url = new URL(urlText);
			} catch (ConnectionException e) {
				throw new ShowStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
			} catch (ParseException | MalformedURLException e) {
				throw new ShowStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
			}
		}
	}
}
