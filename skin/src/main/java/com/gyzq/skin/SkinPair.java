package com.gyzq.skin;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description: 一个属性对应一个资源id
 */
public class SkinPair {

    public SkinPair(String attributeName, int resId) {
        this.attributeName = attributeName;
        this.resId = resId;
    }

    /**
     * 属性名
     */
    private String attributeName;
    /**
     * 资源id
     */
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
