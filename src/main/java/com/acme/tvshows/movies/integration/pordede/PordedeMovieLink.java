package com.acme.tvshows.movies.integration.pordede;

import com.acme.tvshows.movies.model.ErrorType;
import com.acme.tvshows.movies.model.Language;
import com.acme.tvshows.movies.model.MovieLink;
import com.acme.tvshows.movies.model.MovieStoreException;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PordedeMovieLink implements MovieLink {
    private final ParseHelper parseHelper;
    private final String initialUrl;
    private final String server;
    private final Language language;
    private final String videoQuality;
    private final String audioQuality;
    private URL url;

    public PordedeMovieLink(ParseHelper parseHelper, Element linkElement) {
        this.parseHelper = parseHelper;
        PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
        this.initialUrl = linkElement.attr(config.getLinkUrlAttr());
        this.server = extractServerName(
                linkElement.select(config.getLinkServerSelect()).first().attr(config.getLinkServerAttr()),
                config.getLinkServerRegex());
        this.language = new PordedeMovieLanguage(linkElement.select(config.getLinkLanguageSelect()));
        this.videoQuality = linkElement.select(config.getLinkVideoQualitySelect()).first().ownText().trim();
        this.audioQuality = linkElement.select(config.getLinkAudioQualitySelect()).first().ownText().trim();
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
    public String getVideoQuality() {
        return videoQuality;
    }

    @Override
    public String getAudioQuality() {
        return audioQuality;
    }

    @Override
    public URL getUrl() throws MovieStoreException {
        if (url == null) {
            buildRealUrl();
        }
        return url;
    }

    private synchronized void buildRealUrl() throws MovieStoreException {
        if (url == null) {
            PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
            try {
                Document document = parseHelper.parseUrl(initialUrl);
                String urlText = document.select(config.getLinkRealUrlSelect()).first().attr(config.getLinkRealUrlAttr());
                String redirectUrl = parseHelper.getRedirectUrl(urlText);
                url = new URL(redirectUrl);
            } catch (ConnectionException | MalformedURLException e) {
                throw new MovieStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
            } catch (ParseException e) {
                throw new MovieStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
            }
        }
    }
}
