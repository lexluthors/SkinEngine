package com.gyzq.skinengine;


import android.app.Application;

import com.gyzq.skin.SkinManager;
import com.gyzq.skin.display.ScreenAdaptationUtil;
import com.gyzq.skin.language.LanguageManager;


/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdaptationUtil.getInstance().setDefaultDp(375).setOpenAdaptation(true);
        SkinManager.init(this);

        //new Language(Language.MODE.CUSTOM, LanguageManager.getTWLanguage())
        LanguageManager.init(this);
        System.out.println("当前线程是多少>>>>"+Thread.currentThread().getName());
    }
}
