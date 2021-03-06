package com.acme.tvshows.tv.integration.pordede;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.tv.model.ErrorType;
import com.acme.tvshows.tv.model.Language;
import com.acme.tvshows.tv.model.Link;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.tv.model.ShowStoreException;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PordedeLink implements Link {
    private final ParseHelper parseHelper;
    private final String initialUrl;
    private final String server;
    private final Language language;
    private URL url;

    public PordedeLink(ParseHelper parseHelper, Element linkElement) {
        this.parseHelper = parseHelper;
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        this.initialUrl = linkElement.attr(config.getLinkUrlAttr());
        this.server = extractServerName(
                linkElement.select(config.getLinkServerSelect()).first().attr(config.getLinkServerAttr()),
                config.getLinkServerRegex());
        this.language = new PordedeLanguage(linkElement.select(config.getLinkLanguageSelect()));
    }

    private String extractServerName(String rawServer, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(rawServer);
        return matcher.find() ? matcher.group(1) : rawServer;
    }

    @Override
    public String getId() {
        return initialUrl.substring(initialUrl.lastIndexOf('/') + 1);
    }

    @Override
    public String getServer() {
        return server;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public URL getUrl() throws ShowStoreException {
        if (url == null) {
            buildRealUrl();
        }
        return url;
    }

    private synchronized void buildRealUrl() throws ShowStoreException {
        if (url == null) {
            PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
            try {
                Document document = parseHelper.parseUrl(initialUrl);
                String urlText = document.select(config.getLinkRealUrlSelect()).first().attr(config.getLinkRealUrlAttr());
                String redirectUrl = parseHelper.getRedirectUrl(urlText);
                url = new URL(redirectUrl);
            } catch (ConnectionException | MalformedURLException e) {
                throw new ShowStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
            } catch (ParseException e) {
                throw new ShowStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
            }
        }
    }
}
