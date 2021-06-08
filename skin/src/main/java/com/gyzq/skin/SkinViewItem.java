package com.gyzq.skin;

import android.view.View;

import java.util.ArrayList;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description: 保存皮肤
 */
public class SkinViewItem {

    private View view;

    // Pair中保存属性及对应的resId
    private ArrayList<SkinPair> skinAttrs;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ArrayList<SkinPair> getSkinAttrs() {
        return skinAttrs;
    }

    public void setSkinAttrs(ArrayList<SkinPair> skinAttrs) {
        this.skinAttrs = skinAttrs;
    }

}
