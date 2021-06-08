package com.gyzq.skinengine;


import android.app.Application;

import com.gyzq.skin.SkinManager;


/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
