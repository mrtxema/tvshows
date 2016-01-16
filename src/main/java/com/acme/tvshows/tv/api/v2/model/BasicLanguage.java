package com.acme.tvshows.tv.api.v2.model;

import com.acme.tvshows.tv.model.Language;

public class BasicLanguage {
    final String code;
    final String name;

    BasicLanguage(Language language) {
        code = language.getCode();
        name = language.getName();
    }
}
