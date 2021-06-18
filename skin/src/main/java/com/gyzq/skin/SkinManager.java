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

    public Application mContext;
    private static volatile SkinManager mInstance;

    public static final ArrayList<String> mSupportAttr = new ArrayList<>();

    static {
        mSupportAttr.add("background");
        mSupportAttr.add("textColor");
        mSupportAttr.add("src");
        mSupportAttr.add("drawableLeft");
        mSupportAttr.add("drawableTop");
        mSupportAttr.add("drawableRight");
        mSupportAttr.add("drawableBottom");
        mSupportAttr.add("skinTypeface");
        mSupportAttr.add("drawableStart");
        mSupportAttr.add("drawableEnd");
    }

    public static final String[] mClassPrefixList = {
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
        //记录当前使用的皮肤
        SkinSpUtils.init(application);
        //资源管理类 用于从app/皮肤中加载资源
        SkinResUtils.init(application);
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks());
        //加载当前皮肤，默认皮肤
        loadSkin(SkinSpUtils.getInstance().getSkin());
        SkinResUtils.getInstance().applyTypeFace();
    }

    /**
     * 加载指定的皮肤包
     *
     * @param path 皮肤包路径，本地路径
     */
    public void loadSkin(String path) {
        System.out.println("开始时间》》》》"+System.currentTimeMillis());
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
}
