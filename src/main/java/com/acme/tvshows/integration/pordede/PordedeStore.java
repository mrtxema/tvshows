package com.acme.tvshows.integration.pordede;

import com.acme.tvshows.integration.ParseHelper;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Show;
import com.acme.tvshows.model.Store;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class PordedeStore implements Store {
    private static final int HTTP_OK = 200;
    private static final String USERNAME_PARAMETER = "username";

    private final ParseHelper parseHelper;
    private final String loginUrl;
    private final String loginFormName;
    private final String sessionCheckUrl;
    private final String searchUrl;
    private final String showSelect;
    private final String name;

    public PordedeStore() {
        parseHelper = new ParseHelper();
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        loginUrl = config.getStoreLoginUrl();
        loginFormName = config.getStoreLoginFormName();
        sessionCheckUrl = config.getStoreSessionCheckUrl();
        searchUrl = config.getStoreSearchUrl();
        showSelect = config.getStoreShowSelect();
        name = config.getStoreName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Show> findShows(String showName) throws ConnectionException, ParseException {
        Document document = parseHelper.parseUrl(String.format(searchUrl, showName));
        List<Show> shows = new ArrayList<Show>();
        for (Element showElement : document.select(showSelect)) {
            shows.add(new PordedeShow(parseHelper, showElement));
        }
        return shows;
    }

    @Override
    public Show getShow(String id) throws ConnectionException, ParseException {
        return new PordedeShow(parseHelper, id);
    }

    private Map<String, String> adaptFormParameters(String formName, Map<String, String> parameters) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            result.put(String.format("%s[%s]", formName, entry.getKey()), entry.getValue());
        }
        return result;
    }

    @Override
    public boolean login(Map<String, String> parameters) throws ConnectionException {
        return (parseHelper.getPostResponseCode(loginUrl, adaptFormParameters(loginFormName, parameters)) == HTTP_OK)
                && (parseHelper.getResponseCode(String.format(sessionCheckUrl, parameters.get(USERNAME_PARAMETER))) == HTTP_OK);
    }
}
