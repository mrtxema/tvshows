package com.acme.tvshows.video.integration.streamcloud;

import com.acme.tvshows.parser.ConnectionException;
import com.acme.tvshows.parser.ParseException;
import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.video.model.ErrorType;
import com.acme.tvshows.video.model.VideoLinkException;
import com.acme.tvshows.video.model.VideoStore;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamcloudVideoStore implements VideoStore {
    private final ParseHelper parseHelper;
    private final String name;
    private final String postDataPattern;
    private final long waitTimeMillis;
    private final int numRetries;
    private final String scriptSelect;
    private final Pattern linkPattern;

    public StreamcloudVideoStore() {
        parseHelper = new ParseHelper();
        StreamcloudConfiguration config = BeanFactory.getInstance(StreamcloudConfiguration.class);
        name = config.getStoreName();
        postDataPattern = config.getPostDataPattern();
        waitTimeMillis = config.getWaitTimeMillis();
        numRetries = config.getNumRetries();
        scriptSelect = config.getScriptSelect();
        linkPattern = Pattern.compile(config.getLinkPattern());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVideoUrl(String link) throws VideoLinkException {
        try {
            String[] pathComponents = new URL(link).getPath().split("/");
            String fname = pathComponents[2].substring(0, pathComponents[2].lastIndexOf('.'));
            return retrieveVideoLink(link, buildParameters(String.format(postDataPattern, pathComponents[1], fname)));
        } catch (MalformedURLException e) {
            throw new VideoLinkException(ErrorType.INVALID_ARGUMENT, e.getMessage(), e);
        } catch (ConnectionException e) {
            throw new VideoLinkException(ErrorType.CONNECTION_ERROR, e.getMessage(), e);
        } catch (ParseException e) {
            throw new VideoLinkException(ErrorType.PARSE_ERROR, e.getMessage(), e);
        }
    }

    private String retrieveVideoLink(String link, Map<String, String> parameters) throws ConnectionException, ParseException {
        for (int i = 0; i < numRetries; i++) {
            if (i > 0) {
                try {
                    Thread.sleep(waitTimeMillis);
                } catch (InterruptedException e) {
                }
            }
            Document document = parseHelper.parsePostUrl(link, parameters);
            for (Element scriptElement : document.select(scriptSelect)) {
                Matcher matcher = linkPattern.matcher(scriptElement.data());
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }
        return null;
    }

    private Map<String, String> buildParameters(String postData) {
        Map<String,String> result = new HashMap<>();
        for (String entry : postData.split("&")) {
            String[] entryInfo = entry.split("=");
            String value = entryInfo.length < 2 ? "" : entryInfo[1];
            result.put(entryInfo[0], value);
        }
        return result;
    }
}
