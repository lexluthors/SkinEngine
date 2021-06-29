package com.gyzq.skin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description: 加载皮肤的资源管理器工具类
 */
public class SkinResUtils {

    private static volatile SkinResUtils mInstance;
    //app内部的resource对象
    private Resources mAppResources;
    //用于加载外部皮肤的resource对象
    private Resources mSkinResources;
    //皮肤包的包名
    private String mSkinPackageName;
    //是否是默认皮肤
    private boolean isDefaultSkin = true;
    //默认字体
    private Typeface typeFace = Typeface.DEFAULT;

    Context context;

    private SkinResUtils(Context context) {
        mAppResources = context.getResources();
        //初始化本地保存的当前使用的字体，通过字体的本地路径获取
        typeFace = getTypeFaceFromFilePath();
        this.context = context;
    }

    public static SkinResUtils getInstance() {
        return mInstance;
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (SkinResUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinResUtils(context);
                }
            }
        }
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinPackageName = pkgName;
        mSkinResources = resources;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

    public void reset() {
        mSkinResources = null;
        mSkinPackageName = "";
        isDefaultSkin = true;
    }

    public Typeface getTypeface() {
        return typeFace;
    }

    public void setTypeface() {
        this.typeFace = getTypeFaceFromFilePath();
    }

    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        //如果有皮肤  isDefaultSkin false 没有就是true
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }

    /**
     * 可能是Color 也可能是drawable
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        //在皮肤包中不一定就是 当前程序的 id
        //获取对应id 在当前的名称 colorPrimary
        //R.drawable.ic_launcher
        String resName = mAppResources.getResourceEntryName(resId);//ic_launcher   /colorPrimaryDark
        String resType = mAppResources.getResourceTypeName(resId);//drawable
        return mSkinResources.getIdentifier(resName, resType, mSkinPackageName);
    }

    /**
     * 根据字体ID来获取实际的字体对象
     *
     * @param skinTypefaceId 属性id
     */
    public Typeface getTypeface(int skinTypefaceId) {
        String skinTypefacePath = getString(skinTypefaceId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            //看出来咱们定义的字体串为空的意义了么？如果为空则就直接用系统的默认字体啦
            return Typeface.DEFAULT;
        }
        try {
            if (isDefaultSkin) {
                return Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
            }
            return Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
        } catch (Exception ignored) {
        }
        return Typeface.DEFAULT;
    }

    private String getString(int skinTypefaceId) {
        try {
            //使用默认皮肤
            if (isDefaultSkin) {
                //使用app 设置的属性值
                return mAppResources.getString(skinTypefaceId);
            }
            int skinId = getIdentifier(skinTypefaceId);
            if (skinId == 0) {
                //使用app 设置的属性值
                return mAppResources.getString(skinTypefaceId);
            }
            return mSkinResources.getString(skinId);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * "/sdcard/specified.ttf"
     */
    public Typeface getTypeFaceFromFilePath() {
        String fontPath = SkinSpUtils.getInstance().getTypeFacePath();
        if (TextUtils.isEmpty(fontPath))
            return Typeface.DEFAULT;
        return Typeface.createFromFile(fontPath);
    }

    /**
     * 全局设置字体
     */
    public Typeface applyTypeFace() {
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, typeFace);
            return typeFace;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Typeface.DEFAULT;
    }

    public void changeFontScale(float fontScale) {
        Configuration con = mAppResources.getConfiguration();
        con.fontScale = fontScale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            context.createConfigurationContext(con);
//        } else {
//        }
        mAppResources.updateConfiguration(con, mAppResources.getDisplayMetrics());

    }

    public void changeFontScale(Activity activity, float fontScale) {
        Configuration con = activity.getResources().getConfiguration();
        con.fontScale = fontScale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activity.createConfigurationContext(con);
        } else {
            activity.getResources().updateConfiguration(con, activity.getResources().getDisplayMetrics());
        }

    }

}
