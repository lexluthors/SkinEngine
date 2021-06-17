package com.gyzq.multilanguage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author: liujie
 * @date: 2021/6/16
 * @description:
 */
public class LanguageManager {
    private static Language mLanguage;
    private static Map<String, Locale> mSupportLanguages;

    public static void init(Language language){
        mLanguage = language;
        final List<Locale> locales = language.getLocales();
        mSupportLanguages = new HashMap<String, Locale>(locales.size()) {{
            for (Locale locale: locales) {
                put(locale.getLanguage(), locale);
            }
        }};
    }

    /**
     * 初始化语言
     * @param context
     * @return
     */
    public static Context attachBaseContext(Context context) {
        String saveLanguage = LanguageManager.getSaveLanguage(context);
//        LogUtils.logi("当前语言:>>>"+saveLanguage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, saveLanguage);
        } else {
            applyLanguage(context, saveLanguage);
            return context;
        }
    }

    /**
     * 获取当前的语言
     * @param context
     * 1.若已设置过则获取设置过的语言
     * 2.若未设置:
     *    1.若为跟随系统(Language.MODE.AUTO): 返回当前系统语言
     *    2.若为自定义(Language.MODE.CUSTOM): 返回自定义的默认语言,若未设置,则默认为中文
     *
     * @return 语言
     */
    public static String getSaveLanguage(Context context) {
        //获取首选语言
        String preferredLanguage = getPreferredLanguage().getLanguage();
        return LanguageSpUtils.getInstance().getLanguage();
    }

    /**
     * 更新保存语言
     * @param context
     * @param language
     */
    public static void setSaveLanguage(Context context, String language){
        LanguageSpUtils.getInstance().setLanguage(language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(language);
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private static void applyLanguage(Context context, String newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = getSupportLanguage(newLanguage);
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }

    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    private static boolean isSupportLanguage(String language) {
        return mSupportLanguages.containsKey(language);
    }

    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    @TargetApi(Build.VERSION_CODES.N)
    private static Locale getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mSupportLanguages.get(language);
        }
        return getPreferredLanguage();
    }

    /**
     * 获取首选语言
     * 1.自动模式下: 优先选择系统语言
     * 2.自定义模式下: 优先选择设置的默认语言
     * @return Locale
     */
    public static Locale getPreferredLanguage() {
        Locale locale;
        //跟随系统
        if (mLanguage.getMode() == Language.MODE.AUTO){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }
        }else {
            locale = mLanguage.getDefaultLocale();
        }

        return locale;
    }
}
