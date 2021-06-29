package com.gyzq.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description: 用于保存皮肤路径
 */
public class SkinSpUtils {
    private static final String SKIN_SHARED = "gyzq_skins";

    private static final String KEY_SKIN_PATH = "key_skin_path";
    private static final String KEY_TYPEFACE_PATH = "key_typeface_path";
    //字体大小
    private static final String KEY_FONT_SCALE = "key_font_scale";
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

    public void setTypeFacePath(String fontPath) {
        pref.edit().putString(KEY_TYPEFACE_PATH, fontPath).apply();
    }

    public String getTypeFacePath() {
        return pref.getString(KEY_TYPEFACE_PATH, "");
    }

    public void saveFontScale(float fontScale) {
        pref.edit().putFloat(KEY_FONT_SCALE, fontScale).apply();
    }

    public float getFontScale() {
       return pref.getFloat(KEY_FONT_SCALE, 1f);
    }

}
