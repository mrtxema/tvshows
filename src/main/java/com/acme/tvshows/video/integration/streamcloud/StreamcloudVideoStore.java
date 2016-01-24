package com.acme.tvshows.video.integration.streamcloud;

import com.acme.tvshows.parser.ParseHelper;
import com.acme.tvshows.util.BeanFactory;
import com.acme.tvshows.video.model.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamcloudVideoStore implements VideoStore {
    private final ParseHelper parseHelper;
    private final String name;
    private final long waitTimeMillis;
    private final String scriptSelect;
    private final Pattern linkPattern;

    public StreamcloudVideoStore() {
        parseHelper = new ParseHelper();
        StreamcloudConfiguration config = BeanFactory.getInstance(StreamcloudConfiguration.class);
        name = config.getStoreName();
        waitTimeMillis = config.getWaitTimeMillis();
        scriptSelect = config.getScriptSelect();
        linkPattern = Pattern.compile(config.getLinkPattern());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NavigationResponse navigate(NavigationRequest request) {
        Document document = parseHelper.parseHtml(request.getServerResponse(), request.getNavigationAction().getUri());
        for (Element scriptElement : document.select(scriptSelect)) {
            Matcher matcher = linkPattern.matcher(scriptElement.data());
            if (matcher.find()) {
                return new NavigationResponse(matcher.group(1));
            }
        }
        try {
            Thread.sleep(waitTimeMillis);
        } catch (InterruptedException e) {
        }
        return new NavigationResponse(request.getNavigationAction());
    }
}
