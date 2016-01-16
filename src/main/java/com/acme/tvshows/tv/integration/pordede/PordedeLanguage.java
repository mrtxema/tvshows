package com.acme.tvshows.tv.integration.pordede;

import com.acme.tvshows.tv.model.Language;
import com.acme.tvshows.util.BeanFactory;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PordedeLanguage implements Language {
    private final String code;
    private final String name;

    public PordedeLanguage(Elements elementList) {
        PordedeConfiguration config = BeanFactory.getInstance(PordedeConfiguration.class);
        StringBuffer code = new StringBuffer();
        StringBuffer name = new StringBuffer();
        for (Element element : elementList) {
            String language = element.attr(config.getLanguageCodeAttr()).split(" ")[1];
            String extra = element.text().replaceAll("\u00a0", " ").trim();
            code.append(language.substring(0, 2));
            if (name.length() > 0) {
                name.append(", ");
            }
            name.append(language);
            if (extra.length() > 0) {
                name.append(" ");
                name.append(extra);
            }
        }
        this.code = code.toString();
        this.name = name.toString();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
