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
    private float defaultDp = 360;
    /**
     * 是否开启适配
     */
    private boolean isOpenAdaptation = false;

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

    public void setOpenAdaptation(boolean openAdaptation) {
        isOpenAdaptation = openAdaptation;
    }

    /**
     * android中的dp在渲染前会将dp转为px，dpi即是ppi，像素密度。计算公式：
     * px = density * dp;
     * density = dpi / 160;
     * px = dp * (dpi / 160);
     *
     * 屏幕适配 其主要的作用就是将其他尺寸单位（例如dp，sp）转换为像素单位px。现在我们来测试一下，举个例子：
     *
     * 例1：设计图总宽度为375dp，屏幕宽度为1080px，可以得出density，1080/375=2.88。
     * 假如一个view为60dp，60dp算成px就是 60dp*2.88=172.8px，所占屏幕宽度比为 172.8/1080=0.16
     *
     * 例2：设计图总宽度为375dp，屏幕宽度为1440px，可以得出density，1440/375=3.84。
     * 假如一个view为60dp，60dp那么算成px就是 60dp*3.84=230.4px，所占屏幕宽度比为 230.4/1440=0.16。
     *
     * @param activity
     * @param application
     */
    public void setCustomDensity(Activity activity, Application application) {
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

//    /**
//     * 系统源码 尺寸之间的转换，最终都会转成px显示到屏幕上
//     * @param unit
//     * @param value
//     * @param metrics
//     * @return
//     */
//    public static float applyDimension(int unit, float value,
//                                       DisplayMetrics metrics)
//    {
//        switch (unit) {
//            case COMPLEX_UNIT_PX:
//                return value;
//            case COMPLEX_UNIT_DIP:
//                return value * metrics.density;
//            case COMPLEX_UNIT_SP:
//                return value * metrics.scaledDensity;
//            case COMPLEX_UNIT_PT:
//                return value * metrics.xdpi * (1.0f/72);
//            case COMPLEX_UNIT_IN:
//                return value * metrics.xdpi;
//            case COMPLEX_UNIT_MM:
//                return value * metrics.xdpi * (1.0f/25.4f);
//        }
//        return 0;
//    }
}
