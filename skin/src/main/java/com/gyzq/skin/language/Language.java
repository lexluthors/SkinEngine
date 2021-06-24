package com.gyzq.skin.language;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author: liujie
 * @date: 2021/6/16
 * @description:
 */
public class Language {
    //默认支持的语言集合
    private static final List<Locale> DEFAULT_LOCALES = Arrays.asList(LanguageManager.getZhLanguage(), LanguageManager.getEnLanguage(), LanguageManager.getTWLanguage());
    //自定义模式下,默认语言
    private static final Locale DEFAULT_LOCALE = Locale.CHINA;
    private MODE mode;
    private Locale defaultLocale;
    private List<Locale> locales;

    public Language(MODE mode, Locale defaultLocale, List<Locale> locales) {
        this.mode = mode;
        if (defaultLocale == null) {
            defaultLocale = DEFAULT_LOCALE;
        }
        if (locales == null) {
            locales = DEFAULT_LOCALES;
        }
        this.defaultLocale = defaultLocale;
        this.locales = locales;
    }

    public Language(MODE mode, Locale defaultLocale) {
        this(mode, defaultLocale, null);
    }

    public Language(MODE mode) {
        this(mode, null, null);
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }

    public MODE getMode() {
        return mode;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public enum MODE {
        AUTO,
        CUSTOM
    }
}
