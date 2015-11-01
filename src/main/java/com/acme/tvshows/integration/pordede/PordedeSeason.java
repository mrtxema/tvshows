package com.acme.tvshows.integration.pordede;

import com.acme.tvshows.integration.ParseHelper;
import com.acme.tvshows.model.ConnectionException;
import com.acme.tvshows.model.Episode;
import com.acme.tvshows.model.ParseException;
import com.acme.tvshows.model.Season;
import org.jsoup.nodes.Element;

import java.util.List;

public class PordedeSeason implements Season {

    public PordedeSeason(ParseHelper parseHelper, Element seasonElement) {
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public List<Episode> getEpisodes() throws ConnectionException, ParseException {
        return null;
    }

    @Override
    public Episode getEpisode(int episodeNumber) throws ConnectionException, ParseException {
        return null;
    }
}
