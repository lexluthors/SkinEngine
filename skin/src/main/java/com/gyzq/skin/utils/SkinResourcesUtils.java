package com.gyzq.skin.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class SkinResourcesUtils {

    private static volatile SkinResourcesUtils mInstance;
    private Resources mAppResources;
    private Resources mSkinResources;
    private String mSkinPackageName;
    private boolean isDefaultSkin = true;

    private SkinResourcesUtils(Context context) {
        mAppResources = context.getResources();
    }

    public static SkinResourcesUtils getInstance() {

        return mInstance;
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (SkinResourcesUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinResourcesUtils(context);
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
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPackageName);
        return skinId;
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
        } catch (Exception e) {
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
}
