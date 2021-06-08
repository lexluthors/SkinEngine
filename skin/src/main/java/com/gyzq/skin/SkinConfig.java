package com.gyzq.skin;


/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class SkinConfig {
    public static final String NAMESPACE = "http://schemas.android.com/android/skin";
    public static final String PREF_CUSTOM_SKIN_PATH = "skin_custom_path";
    public static final String PREF_FONT_PATH = "skin_font_path";
    //默认皮肤存储路径key，当前皮肤存储key
    public static final String KEY_SKIN_PATH = "key_skin_path";
    public static final String ATTR_SKIN_ENABLE = "enable";
    public static final String PREF_NIGHT_MODE = "night_mode";

    public static final String SKIN_DIR_NAME = "skin";
    public static final String FONT_DIR_NAME = "fonts";
    private static boolean isCanChangeStatusColor = false;
    private static boolean isCanChangeFont = false;
    private static boolean isDebug = false;
    private static boolean isGlobalSkinApply = false;

//    /**
//     * get path of last skin package path
//     *
//     * @param context
//     * @return path of skin package
//     */
//    public static String getCustomSkinPath(Context context) {
//        return SkinPreferencesUtils.get(context, PREF_CUSTOM_SKIN_PATH, DEFAULT_SKIN);
//    }
//
//    /**
//     * save the skin's path
//     *
//     * @param context
//     * @param path
//     */
//    public static void saveSkinPath(Context context, String path) {
//        SkinPreferencesUtils.put(context, PREF_CUSTOM_SKIN_PATH, path);
//    }
//
//    public static void saveFontPath(Context context, String path) {
//        SkinPreferencesUtils.put(context, PREF_FONT_PATH, path);
//    }
//
//    public static boolean isDefaultSkin(Context context) {
//        return DEFAULT_SKIN.equals(getCustomSkinPath(context));
//    }
//
//    public static void setNightMode(Context context, boolean isEnableNightMode) {
//        SkinPreferencesUtils.put(context, PREF_NIGHT_MODE, isEnableNightMode);
//    }
//
//    public static boolean isInNightMode(Context context) {
//        return SkinPreferencesUtils.get(context, PREF_NIGHT_MODE, false);
//    }

    public static void setCanChangeStatusColor(boolean isCan) {
        isCanChangeStatusColor = isCan;
    }

    public static boolean isCanChangeStatusColor() {
        return isCanChangeStatusColor;
    }

    public static void setCanChangeFont(boolean isCan) {
        isCanChangeFont = isCan;
    }

    public static boolean isCanChangeFont() {
        return isCanChangeFont;
    }

    public static void setDebug(boolean enable) {
        isDebug = enable;
    }

    public static boolean isDebug() {
        return isDebug;
    }

//    /**
//     * add custom skin attribute support
//     *
//     * @param attrName attribute name
//     * @param skinAttr skin attribute
//     */
//    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
//        AttrFactory.addSupportAttr(attrName, skinAttr);
//    }

    public static boolean isGlobalSkinApply() {
        return isGlobalSkinApply;
    }

    public static void enableGlobalSkinApply() {
        isGlobalSkinApply = true;
    }

}
