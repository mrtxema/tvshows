package com.acme.tvshows.movies.integration.pordede;

import com.acme.tvshows.util.Configured;
import com.acme.tvshows.util.Singleton;

@Singleton
@Configured
public class PordedeMovieConfiguration {
    private String storeLoginUrl;
    private String storeLoginFormName;
    private String storeSessionCheckUrl;
    private String storeSearchUrl;
    private String storeMovieSelect;
    private String storeName;
    private String movieUrlPattern;
    private String movieSearchIdAttr;
    private String movieSearchNameSelect;
    private String movieSearchNameAttr;
    private String movieNameSelect;
    private String movieRealLinkSelect;
    private String linkUrlAttr;
    private String linkRealUrlSelect;
    private String linkRealUrlAttr;
    private String linkServerSelect;
    private String linkServerAttr;
    private String linkLanguageSelect;
    private String languageCodeAttr;
    private String linkServerRegex;
    private String linkVideoQualitySelect;
    private String linkAudioQualitySelect;

    public String getStoreLoginUrl() {
        return storeLoginUrl;
    }

    public void setStoreLoginUrl(String storeLoginUrl) {
        this.storeLoginUrl = storeLoginUrl;
    }

    public String getStoreLoginFormName() {
        return storeLoginFormName;
    }

    public void setStoreLoginFormName(String storeLoginFormName) {
        this.storeLoginFormName = storeLoginFormName;
    }

    public String getStoreSessionCheckUrl() {
        return storeSessionCheckUrl;
    }

    public void setStoreSessionCheckUrl(String storeSessionCheckUrl) {
        this.storeSessionCheckUrl = storeSessionCheckUrl;
    }

    public String getStoreSearchUrl() {
        return storeSearchUrl;
    }

    public void setStoreSearchUrl(String storeSearchUrl) {
        this.storeSearchUrl = storeSearchUrl;
    }

    public String getStoreMovieSelect() {
        return storeMovieSelect;
    }

    public void setStoreMovieSelect(String storeMovieSelect) {
        this.storeMovieSelect = storeMovieSelect;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMovieUrlPattern() {
        return movieUrlPattern;
    }

    public void setMovieUrlPattern(String movieUrlPattern) {
        this.movieUrlPattern = movieUrlPattern;
    }

    public String getMovieSearchIdAttr() {
        return movieSearchIdAttr;
    }

    public void setMovieSearchIdAttr(String movieSearchIdAttr) {
        this.movieSearchIdAttr = movieSearchIdAttr;
    }

    public String getMovieSearchNameSelect() {
        return movieSearchNameSelect;
    }

    public void setMovieSearchNameSelect(String movieSearchNameSelect) {
        this.movieSearchNameSelect = movieSearchNameSelect;
    }

    public String getMovieSearchNameAttr() {
        return movieSearchNameAttr;
    }

    public void setMovieSearchNameAttr(String movieSearchNameAttr) {
        this.movieSearchNameAttr = movieSearchNameAttr;
    }

    public String getMovieNameSelect() {
        return movieNameSelect;
    }

    public void setMovieNameSelect(String movieNameSelect) {
        this.movieNameSelect = movieNameSelect;
    }

    public String getMovieRealLinkSelect() {
        return movieRealLinkSelect;
    }

    public void setMovieRealLinkSelect(String movieRealLinkSelect) {
        this.movieRealLinkSelect = movieRealLinkSelect;
    }

    public String getLinkUrlAttr() {
        return linkUrlAttr;
    }

    public void setLinkUrlAttr(String linkUrlAttr) {
        this.linkUrlAttr = linkUrlAttr;
    }

    public String getLinkRealUrlSelect() {
        return linkRealUrlSelect;
    }

    public void setLinkRealUrlSelect(String linkRealUrlSelect) {
        this.linkRealUrlSelect = linkRealUrlSelect;
    }

    public String getLinkRealUrlAttr() {
        return linkRealUrlAttr;
    }

    public void setLinkRealUrlAttr(String linkRealUrlAttr) {
        this.linkRealUrlAttr = linkRealUrlAttr;
    }

    public String getLinkServerSelect() {
        return linkServerSelect;
    }

    public void setLinkServerSelect(String linkServerSelect) {
        this.linkServerSelect = linkServerSelect;
    }

    public String getLinkServerAttr() {
        return linkServerAttr;
    }

    public void setLinkServerAttr(String linkServerAttr) {
        this.linkServerAttr = linkServerAttr;
    }

    public String getLinkServerRegex() {
        return linkServerRegex;
    }

    public void setLinkServerRegex(String linkServerRegex) {
        this.linkServerRegex = linkServerRegex;
    }

    public String getLinkLanguageSelect() {
        return linkLanguageSelect;
    }

    public void setLinkLanguageSelect(String linkLanguageSelect) {
        this.linkLanguageSelect = linkLanguageSelect;
    }

    public String getLanguageCodeAttr() {
        return languageCodeAttr;
    }

    public void setLanguageCodeAttr(String languageCodeAttr) {
        this.languageCodeAttr = languageCodeAttr;
    }

    public String getLinkVideoQualitySelect() {
        return linkVideoQualitySelect;
    }

    public void setLinkVideoQualitySelect(String linkVideoQualitySelect) {
        this.linkVideoQualitySelect = linkVideoQualitySelect;
    }

    public String getLinkAudioQualitySelect() {
        return linkAudioQualitySelect;
    }

    public void setLinkAudioQualitySelect(String linkAudioQualitySelect) {
        this.linkAudioQualitySelect = linkAudioQualitySelect;
    }
}
