package com.gyzq.skin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

/**
 * @author: liujie
 * @date: 2021/6/10
 * @description:
 */
public class SkinThemeUtils {

    private static final int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.R.attr.colorPrimaryDark
    };
    private static final int[] STATUS_BAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr
            .navigationBarColor};

    //换肤字体的自定义属性名
    private static final int[] TYPEFACE_ATTRS = {
            android.R.attr.text
    };

    public static int[] getResId(Context context, int[] attrs) {
        int[] ints = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            ints[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return ints;
    }

    //替换状态栏
    public static void updataStatusBarColor(Activity activity) {
        //5.0 以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //获取statusBarColor与navigationBarColor  颜色值
        int[] statusBarId = getResId(activity, STATUS_BAR_COLOR_ATTRS);

        if (statusBarId[0] != 0) {
            //如果statusBarColor配置颜色值，就换肤
            activity.getWindow().setStatusBarColor(SkinResUtils.getInstance().getColor(statusBarId[0]));
        } else {
            //获取兼容包中的colorPrimaryDark，兼容版本
            int resId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (resId != 0) {
                activity.getWindow().setStatusBarColor(SkinResUtils.getInstance().getColor(resId));
            }
        }

        if (statusBarId[1] != 0) {
            activity.getWindow().setNavigationBarColor(SkinResUtils.getInstance().getColor(statusBarId[1]));
        }
    }

    public static Typeface getSkinTypeface(Activity activity) {
        //获取字体id
        int skinTypefaceId = getResId(activity, TYPEFACE_ATTRS)[0];
        return SkinResUtils.getInstance().getTypeface(skinTypefaceId);
    }
}
