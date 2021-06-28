package com.gyzq.skin.font;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author: liujie
 * @date: 2021/6/28
 * @description:
 */
public class FontScaleUtil {

    public static void applyFontScale(Context context, float scale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = scale;
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }

}
