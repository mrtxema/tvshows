package com.acme.tvshows.movies.api.v1;

import com.acme.tvshows.movies.api.v1.model.*;
import com.acme.tvshows.movies.integration.MovieStoreManager;
import com.acme.tvshows.movies.integration.MovieStoreType;
import com.acme.tvshows.movies.integration.NavigationService;
import com.acme.tvshows.movies.model.ErrorType;
import com.acme.tvshows.movies.model.MovieStoreException;
import com.acme.tvshows.util.BeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieService {
    private static final int API_VERSION = 1;
    private static final int MINIMUM_SEARCH_LENGTH = 3;
    private final MovieFilterManager filterManager = new MovieFilterManager();

    private Map<String, String> firstValueMap(Map<String, String[]> parameters) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    public List<MovieStore> getAllStoreTypes() {
        List<MovieStore> result = new ArrayList<>();
        for (MovieStoreType type : MovieStoreType.getValues(API_VERSION)) {
            result.add(new MovieStore(type));
        }
        return result;
    }

    public LoginResponse login(String storeCode, Map<String, String[]> parameters) throws MovieStoreException {
        return new LoginResponse(BeanFactory.getInstance(MovieStoreManager.class).newStore(storeCode, firstValueMap(parameters)));
    }

    public SearchResults searchMovies(String storeCode, String token, String searchString) throws MovieStoreException {
        if (searchString == null || searchString.length() < MINIMUM_SEARCH_LENGTH) {
            throw new MovieStoreException(ErrorType.INVALID_ARGUMENT, String.format("Invalid searchString '%s'. Minimum length is %d", searchString, MINIMUM_SEARCH_LENGTH));
        }
        com.acme.tvshows.movies.model.MovieStore store = BeanFactory.getInstance(MovieStoreManager.class).getStore(storeCode, token);
        List<com.acme.tvshows.movies.model.Movie> movies = store.searchMovies(searchString);
        List<Movie> result = new ArrayList<>();
        for (com.acme.tvshows.movies.model.Movie movie : filterManager.filter(com.acme.tvshows.movies.model.Movie.class, movies)) {
            result.add(new Movie(movie));
        }
        return new SearchResults(result);
    }

    public MovieDetails getMovieLinks(String storeCode, String token, String movie) throws MovieStoreException {
        List<MovieLink> result = new ArrayList<>();
        com.acme.tvshows.movies.model.MovieStore store = BeanFactory.getInstance(MovieStoreManager.class).getStore(storeCode, token);
        List<com.acme.tvshows.movies.model.MovieLink> links = store.getMovie(movie).getLinks();
        for (com.acme.tvshows.movies.model.MovieLink link : filterManager.filter(com.acme.tvshows.movies.model.MovieLink.class, links)) {
            result.add(new MovieLink(link));
        }
        return new MovieDetails(result);
    }

    public VideoUrl getLinkUrl(String storeCode, String token, String movie, String linkId) throws MovieStoreException {
        com.acme.tvshows.movies.model.MovieStore store = BeanFactory.getInstance(MovieStoreManager.class).getStore(storeCode, token);
        com.acme.tvshows.movies.model.MovieLink link = store.getMovie(movie).getLink(linkId);
        NavigationService navigationService = BeanFactory.getInstance(NavigationService.class);
        return new VideoUrl(link.getUrl().toString(), navigationService.getNavigationAction(link));
    }
}
