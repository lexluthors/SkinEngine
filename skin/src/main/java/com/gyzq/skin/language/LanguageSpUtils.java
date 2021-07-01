package com.gyzq.skin.language;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: liujie
 * @date: 2021/6/16
 * @description: 保存当前语言的本地存储
 */
public class LanguageSpUtils {
    private static final String SKIN_SHARED = "gyzq_multi_language";
    /**
     * 当前语言sp-key
     */
    private static final String KEY_LANGUAGE_STRING = "key_language_string";
    /**
     * 语言模式sp-key
     */
    private static final String KEY_LANGUAGE_MODE = "key_language_mode";
    private static LanguageSpUtils instance;
    private final SharedPreferences pref;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (LanguageSpUtils.class) {
                if (instance == null) {
                    instance = new LanguageSpUtils(context.getApplicationContext());
                }
            }
        }
    }

    public static LanguageSpUtils getInstance() {
        return instance;
    }

    private LanguageSpUtils(Context context) {
        pref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public void saveLanguage(String language) {
        pref.edit().putString(KEY_LANGUAGE_STRING, language).apply();
    }

    public String getLanguage() {
        return pref.getString(KEY_LANGUAGE_STRING, "");
    }

    public void saveLanguageMode(String languageMode) {
        pref.edit().putString(KEY_LANGUAGE_MODE, languageMode).apply();
    }

    public String getLanguageMode() {
        return pref.getString(KEY_LANGUAGE_MODE, "");
    }

}
