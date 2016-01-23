package com.acme.tvshows.video.integration.streamcloud;

import com.acme.tvshows.util.Configured;
import com.acme.tvshows.util.Singleton;

@Singleton
@Configured
public class StreamcloudConfiguration {
    private String storeName;
    private String postDataPattern;
    private long waitTimeMillis;
    private int numRetries;
    private String scriptSelect;
    private String linkPattern;
    private String proxyBaseUrl;

    public String getPostDataPattern() {
        return postDataPattern;
    }

    public void setPostDataPattern(String postDataPattern) {
        this.postDataPattern = postDataPattern;
    }

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

    public int getNumRetries() {
        return numRetries;
    }

    public void setNumRetries(int numRetries) {
        this.numRetries = numRetries;
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

    public String getProxyBaseUrl() {
        return proxyBaseUrl;
    }

    public void setProxyBaseUrl(String proxyBaseUrl) {
        this.proxyBaseUrl = proxyBaseUrl;
    }
}
