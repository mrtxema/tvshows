package com.acme.tvshows.movies.api.v1.model;

public class Language {
    final String code;
    final String name;

    public Language(com.acme.tvshows.movies.model.Language language) {
        this.code = language.getCode();
        this.name = language.getName();
    }
}
