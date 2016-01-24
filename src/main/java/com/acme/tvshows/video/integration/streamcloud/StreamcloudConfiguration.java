package com.acme.tvshows.video.integration.streamcloud;

import com.acme.tvshows.util.Configured;
import com.acme.tvshows.util.Singleton;

@Singleton
@Configured
public class StreamcloudConfiguration {
    private String storeName;
    private long waitTimeMillis;
    private String scriptSelect;
    private String linkPattern;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getWaitTimeMillis() {
        return waitTimeMillis;
    }

    public void setWaitTimeMillis(long waitTimeMillis) {
        this.waitTimeMillis = waitTimeMillis;
    }

    public String getScriptSelect() {
        return scriptSelect;
    }

    public void setScriptSelect(String scriptSelect) {
        this.scriptSelect = scriptSelect;
    }

    public String getLinkPattern() {
        return linkPattern;
    }

    public void setLinkPattern(String linkPattern) {
        this.linkPattern = linkPattern;
    }
}
