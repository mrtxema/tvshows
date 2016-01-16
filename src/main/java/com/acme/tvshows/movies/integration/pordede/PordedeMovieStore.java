package com.acme.tvshows.movies.integration.pordede;

import com.acme.tvshows.movies.model.ErrorType;
import com.acme.tvshows.movies.model.Movie;
import com.acme.tvshows.movies.model.MovieStore;
import com.acme.tvshows.movies.model.MovieStoreException;
import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.parser.ParseHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PordedeMovieStore implements MovieStore {
    private static final int HTTP_OK = 200;
    private static final String USERNAME_PARAMETER = "username";

    private final ParseHelper parseHelper;
    private final String loginUrl;
    private final String loginFormName;
    private final String sessionCheckUrl;
    private final String searchUrl;
    private final String movieSelect;
    private final String name;

    public PordedeMovieStore() {
        parseHelper = new ParseHelper();
        PordedeMovieConfiguration config = BeanFactory.getInstance(PordedeMovieConfiguration.class);
        loginUrl = config.getStoreLoginUrl();
        loginFormName = config.getStoreLoginFormName();
        sessionCheckUrl = config.getStoreSessionCheckUrl();
        searchUrl = config.getStoreSearchUrl();
        movieSelect = config.getStoreMovieSelect();
        name = config.getStoreName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Movie> searchMovies(String movieName) throws MovieStoreException {
        Document document = null;
        try {
            document = parseHelper.parseUrl(String.format(searchUrl, movieName));
        } catch (ConnectionException e) {
            throw new MovieStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
        } catch (ParseException e) {
            throw new MovieStoreException(ErrorType.PARSE_ERROR, e.getMessage(), e);
        }
        List<Movie> movies = new ArrayList<>();
        for (Element movieElement : document.select(movieSelect)) {
            movies.add(new PordedeMovie(parseHelper, movieElement));
        }
        return movies;
    }

    @Override
    public Movie getMovie(String id) throws MovieStoreException {
        return new PordedeMovie(parseHelper, id);
    }

    private Map<String, String> adaptFormParameters(String formName, Map<String, String> parameters) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            result.put(String.format("%s[%s]", formName, entry.getKey()), entry.getValue());
        }
        return result;
    }

    @Override
    public boolean login(Map<String, String> parameters) throws MovieStoreException {
        try {
            return (parseHelper.getPostResponseCode(loginUrl, adaptFormParameters(loginFormName, parameters)) == HTTP_OK)
                    && (parseHelper.getResponseCode(String.format(sessionCheckUrl, parameters.get(USERNAME_PARAMETER))) == HTTP_OK);
        } catch (ConnectionException e) {
            throw new MovieStoreException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
        }
    }
}
