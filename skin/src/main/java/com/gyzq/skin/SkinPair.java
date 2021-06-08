package com.gyzq.skin;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description:
 */
public class SkinPair {

    public SkinPair(String attributeName, int resId) {
        this.attributeName = attributeName;
        this.resId = resId;
    }

    private String attributeName;
    private int resId;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
