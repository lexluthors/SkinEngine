package com.gyzq.multilanguage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: liujie
 * @date: 2021/6/16
 * @description: 保存当前语言的本地存储
 */
public class LanguageSpUtils {
    private static final String SKIN_SHARED = "gyzq_multi_language";

    private static final String KEY_LANGUAGE_PATH = "key_language_path";
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

    public void setLanguage(String language) {
        pref.edit().putString(KEY_LANGUAGE_PATH, language).apply();
    }

    public String getLanguage() {
        return pref.getString(KEY_LANGUAGE_PATH, "");
    }

}
