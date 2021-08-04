package com.gyzq.skin.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.gyzq.skin.SkinManager;

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
    private static volatile LanguageManager mInstance;

    private static Language.MODE mLanguageMode = Language.MODE.CUSTOM;
    /**
     * 默认中文简体
     */
    private static String mLanguageStr = "zh";
    /**
     * 默认CUSTOM
     */
    private static String mLanguageModeStr = "CUSTOM";

    public static void init(Context context) {
        synchronized (LanguageManager.class) {
            if (null == mInstance) {
                mInstance = new LanguageManager();
            }
            LanguageSpUtils.init(context);
            initPreferredLanguage();
            final List<Locale> locales = mLanguage.getLocales();
            mSupportLanguages = new HashMap<String, Locale>(locales.size()) {{
                for (Locale locale : locales) {
                    put(locale.getLanguage(), locale);
                }
            }};

        }
    }

    public static LanguageManager getInstance() {
        return mInstance;
    }

    private LanguageManager() {
    }


    /**
     * 应用语言
     * @param context
     * @return
     */
    public Context attachBaseContext(Context context) {
        String saveLanguage = getSaveLanguage();
//        LogUtils.logi("当前语言:>>>" + saveLanguage);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return createConfigurationResources(context, saveLanguage);
//        } else {
//            applyLanguage(context, saveLanguage);
//            return context;
//        }
        applyLanguage(context, saveLanguage);
        return context;
    }

    /**
     * 获取当前的语言
     * 1.若已设置过则获取设置过的语言
     * 2.若未设置:
     * 1.若为跟随系统(Language.MODE.AUTO): 返回当前系统语言
     * 2.若为自定义(Language.MODE.CUSTOM): 返回自定义的默认语言,若未设置,则默认为中文
     *
     * @return 语言
     */
    public String getSaveLanguage() {
        //获取首选语言
        return getPreferredLocale().getLanguage();
    }


    @TargetApi(Build.VERSION_CODES.N)
    private Context createConfigurationResources(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(language);
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    /**
     * 提交新的语言
     *
     * @param context
     * @param newLanguage
     */
    private void applyLanguage(Context context, String newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = getSupportLanguage(newLanguage);
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }

    /**
     * 是否支持此语言，一共三种，简繁英
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    private boolean isSupportLanguage(String language) {
        return mSupportLanguages.containsKey(language);
    }

    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    private Locale getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mSupportLanguages.get(language);
        }
        return getPreferredLocale();
    }

    /**
     * 获取首选语言
     * 1.自动模式下: 优先选择系统语言
     * 2.自定义模式下: 优先选择设置的默认语言
     *
     * @return Locale
     */
    public Locale getPreferredLocale() {
        Locale locale;
        //跟随系统
        if (mLanguageMode == Language.MODE.AUTO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }
        } else {
            switch (mLanguageStr) {
                case "TW":
                case "tw":
                    mLanguage = new Language(Language.MODE.CUSTOM, getTwLanguage());
                    break;
                case "en":
                    mLanguage = new Language(Language.MODE.CUSTOM, getEnLanguage());
                    break;
                default:
                    mLanguage = new Language(Language.MODE.CUSTOM, getZhLanguage());
            }
            locale = mLanguage.getDefaultLocale();
        }

        return locale;
    }

    public Language.MODE getLanguageMode() {
        return mLanguageMode;
    }

    public LanguageManager setLanguageMode(Language.MODE mode) {
        mLanguageMode = mode;
        return mInstance;
    }

    public static void initPreferredLanguage() {
        mLanguageModeStr = LanguageSpUtils.getInstance().getLanguageMode();
        if ("AUTO".equals(mLanguageModeStr)) {
            mLanguageMode = Language.MODE.AUTO;
        } else {
            mLanguageMode = Language.MODE.CUSTOM;
        }
        mLanguageStr = LanguageSpUtils.getInstance().getLanguage();
        switch (mLanguageStr) {
            case "tw":
            case "TW":
                mLanguage = new Language(mLanguageMode, getTwLanguage());
                break;
            case "en":
                mLanguage = new Language(mLanguageMode, getEnLanguage());
                break;
            default:
                mLanguage = new Language(mLanguageMode, getZhLanguage());
        }
    }

    public static Locale getTwLanguage() {
        return new Locale("TW", Locale.TRADITIONAL_CHINESE.getCountry());
    }

    public static Locale getEnLanguage() {
        return new Locale("en", Locale.ENGLISH.getCountry());
    }

    public static Locale getZhLanguage() {
        return new Locale("zh", Locale.CHINESE.getCountry());
    }

    public void applyLanguage(Language language) {
        //language变量中的语言模式也要更新
        mLanguage = language;
        //更新保存的当前语言变量
        mLanguageStr = language.getDefaultLocale().getLanguage();
        //把新的语言 保存到sp中
        LanguageSpUtils.getInstance().saveLanguage(mLanguageStr);
        //重新设置新的语言模式
        mLanguageModeStr = language.getMode().name();
        if ("AUTO".equals(mLanguageModeStr)) {
            mLanguageMode = Language.MODE.AUTO;
        } else {
            mLanguageMode = Language.MODE.CUSTOM;
        }
        LanguageSpUtils.getInstance().saveLanguageMode(mLanguageModeStr);
        SkinManager.getInstance().setLanguage();
    }
}
