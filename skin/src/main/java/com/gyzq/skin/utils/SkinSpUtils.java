package com.gyzq.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class SkinSpUtils {
    private static final String SKIN_SHARED = "gyzq_skins";

    private static final String KEY_SKIN_PATH = "key_skin_path";
    private static SkinSpUtils instance;
    private final SharedPreferences pref;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinSpUtils.class) {
                if (instance == null) {
                    instance = new SkinSpUtils(context.getApplicationContext());
                }
            }
        }
    }

    public static SkinSpUtils getInstance() {
        return instance;
    }

    private SkinSpUtils(Context context) {
        pref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public void setSkin(String skinPath) {
        pref.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    public String getSkin() {
        return pref.getString(KEY_SKIN_PATH, "");
    }

}
