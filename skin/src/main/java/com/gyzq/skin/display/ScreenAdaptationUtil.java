package com.gyzq.skin.display;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * @author: liujie
 * @date: 2021/7/5
 * @description: 屏幕适配工具类
 */
public class ScreenAdaptationUtil {
    private static float sComponentDensity;
    private static float sComponentScaleDensity;
    private static ScreenAdaptationUtil instance;
    /**
     * 默认适配宽度，UI横屏尺寸360dp
     */
    float defaultDp = 360;
    /**
     * 是否开启适配
     */
    boolean isOpenAdaptation = false;

    private ScreenAdaptationUtil() {

    }

    public static ScreenAdaptationUtil getInstance() {
        if (null == instance) {
            instance = new ScreenAdaptationUtil();
        }
        return instance;
    }

    public ScreenAdaptationUtil setDefaultDp(float defaultDp) {
        this.defaultDp = defaultDp;
        return instance;
    }

    public boolean isOpenAdaptation() {
        return isOpenAdaptation;
    }

    public void setOpenAdaptation(boolean openAdaptation) {
        isOpenAdaptation = openAdaptation;
    }

    /**
     * 屏幕适配
     * @param activity
     * @param application
     * @param defaultDp
     */
    public void setCustomDensity(Activity activity, Application application, float defaultDp) {
        if (!isOpenAdaptation){
            return;
        }
        // 获取测量屏幕尺寸的DisplayMetrics
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sComponentDensity == 0) {
            sComponentDensity = appDisplayMetrics.density;
            sComponentScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig.fontScale > 0) {
                        sComponentScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        // 获取屏幕的总px，然后除以我们美工设计的总dp，得到我们的目标density
        final float targetDensity = (float) (appDisplayMetrics.widthPixels) / defaultDp;
        final float targetScaledDensity = targetDensity * (sComponentScaleDensity / sComponentDensity);
        // 得到我们的dpi
        final int targetDensityDpi = (int) (160 * targetDensity);
        // 重新设置density和densityDpi
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
