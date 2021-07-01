package com.gyzq.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.gyzq.skin.utils.SkinResUtils;
import com.gyzq.skin.utils.SkinSpUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description: 皮肤管理器
 */
public class SkinManager extends Observable {

    Application mContext;
    /**
     * 当前皮肤包路径
     */
    String mSkinPath;

    /**
     * 皮肤包是否存在本地
     */
    boolean isExistsSkin;
    float mFontScale = 1f;
    /**
     * 上一个缩放倍数
     */
    float mPerFontScale = 1f;
    static volatile SkinManager mInstance;
    static final ArrayList<String> SUPPORT_ATTR = new ArrayList<>();

    static {
        SUPPORT_ATTR.add("background");
        SUPPORT_ATTR.add("textColor");
        SUPPORT_ATTR.add("src");
        SUPPORT_ATTR.add("drawableLeft");
        SUPPORT_ATTR.add("drawableTop");
        SUPPORT_ATTR.add("drawableRight");
        SUPPORT_ATTR.add("drawableBottom");
        SUPPORT_ATTR.add("skinTypeface");
        SUPPORT_ATTR.add("drawableStart");
        SUPPORT_ATTR.add("drawableEnd");
        SUPPORT_ATTR.add("text");
        SUPPORT_ATTR.add("hint");
    }

    public static final String[] CLASS_PREFIX_LIST = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };


    public static void init(Application application) {
        synchronized (SkinManager.class) {
            if (null == mInstance) {
                mInstance = new SkinManager(application);
            }
        }
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    private SkinManager(Application application) {
        mContext = application;
        //实例化皮肤sp，用于访问本地皮肤路径。记录当前使用的皮肤
        SkinSpUtils.init(application);
        //资源管理类 用于从app/皮肤中加载资源
        SkinResUtils.init(application);
        //获取当前使用的皮肤包路径，如果没有使用皮肤就是空串。使用app默认的皮肤。
        mSkinPath = SkinSpUtils.getInstance().getSkin();
        //初始化皮肤是否存在变量
        isExistsSkin = new File(mSkinPath).exists();
        //加载当前皮肤，默认皮肤
        loadSkin(mSkinPath);
        //获取本地保存的字体缩放比，默认是1f
        mFontScale = SkinSpUtils.getInstance().getFontScale();
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks());
        //应用默认字体
        SkinResUtils.getInstance().applyTypeFace();
    }

    public String getSkinPath() {
        return mSkinPath;
    }

    public void setSkinPath(String mSkinPath) {
        this.mSkinPath = mSkinPath;
    }

    public boolean isExistsSkin() {
        return isExistsSkin;
    }

    public void setExistsSkin() {
        mSkinPath = SkinSpUtils.getInstance().getSkin();
        isExistsSkin = new File(mSkinPath).exists();
    }

    public float getFontScale() {
        return mFontScale;
    }

    public float getPerFontScale() {
        return mPerFontScale;
    }

    /**
     * 加载指定的皮肤包
     * @param path 皮肤包路径，本地路径
     */
    public void loadSkin(String path) {
        System.out.println("开始时间》》》》" + System.currentTimeMillis());
        if (!TextUtils.isEmpty(path) && new File(path).exists()) {
            try {
                //反射创建AssetManager
                AssetManager manager = AssetManager.class.newInstance();
                // 皮肤包路径设置 压缩包 apk 或者其他的压缩文件格式 可以是.skin
                Method addAssetPath = manager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(manager, path);

                Resources appResources = mContext.getResources();
                Resources skinResources = new Resources(manager,
                        appResources.getDisplayMetrics(), appResources.getConfiguration());

                //将当前加载的皮肤记录一下
                SkinSpUtils.getInstance().setSkin(path);
                //设置当前皮肤路径
                mSkinPath = path;
                //获取外部Apk（皮肤包）包名
                PackageManager packageManager = mContext.getPackageManager();
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                String packageName = packageArchiveInfo.packageName;

                SkinResUtils.getInstance().applySkin(skinResources, packageName);
                //通知观者者，刷新界面
                setChanged();
                notifyObservers(SkinType.SKIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void resetDefaultSkin() {
        // 记录使用默认皮肤，重置为空，重置为默认皮肤
        SkinSpUtils.getInstance().setSkin("");
        //清空资源管理器、皮肤资源属性等
        SkinResUtils.getInstance().reset();
        mSkinPath = "";
        //通知观者者
        setChanged();
        notifyObservers(SkinType.SKIN);
    }

    public void resetDefaultTypeface() {
        // 记录使用默认皮肤，重置为空，重置为默认皮肤
        setTypeface("");
    }

    public void setTypeface(String path) {
        SkinSpUtils.getInstance().setTypeFacePath(path);
        SkinResUtils.getInstance().setTypeface();
        setChanged();
        notifyObservers(SkinType.TYPEFACE);
        SkinResUtils.getInstance().applyTypeFace();
    }

    /**
     * 通知每个界面切换语言
     */
    public void setLanguage() {
        setChanged();
        notifyObservers(SkinType.LANGUAGE);
    }

    /**
     * 通知每个界面设置字体大小
     * @param fontScale
     */
    public void setFontScale(float fontScale) {
        //赋值当前要设置的缩放倍数
        mFontScale = fontScale;
        //取上一次保存的缩放倍数
        mPerFontScale = SkinSpUtils.getInstance().getFontScale();
        SkinSpUtils.getInstance().saveFontScale(fontScale);
        setChanged();
        notifyObservers(SkinType.FONT_SCALE);
    }
}
