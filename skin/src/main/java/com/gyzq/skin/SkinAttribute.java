package com.gyzq.skin;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyzq.skin.utils.SkinResourcesUtils;
import com.gyzq.skin.utils.SkinSpUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description: 皮肤属性类
 */
public class SkinAttribute {


    private final ArrayList<SkinViewItem> mSkinViewItems;

    public SkinAttribute() {
        this.mSkinViewItems = new ArrayList<>();
    }

    void parseSkinAttr(View view, AttributeSet attributeSet) {
        SkinViewItem skinViewItem = new SkinViewItem();
        skinViewItem.setView(view);

        int size = attributeSet.getAttributeCount();
        ArrayList<SkinPair> skinPairs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String attributeName = attributeSet.getAttributeName(i);
            if (SkinManager.mSupportAttr.contains(attributeName)) {
                // 取值可能为 @2130837590、#具体色值、?2130837590 三种情况
                String attributeValue = attributeSet.getAttributeValue(i);
                //如果是以#开头的资源，是写死的，就不支持换肤，不需要处理
                if (attributeValue.startsWith("#") || attributeValue.startsWith("?")) {
                    continue;
                }
                //@开头的需要处理，@color，@mipmap @drawable
                int resId = 0;
                if (attributeValue.startsWith("@")) {
                    resId = Integer.parseInt(attributeValue.substring(1));
                    if (resId == 0) {
                        continue;
                    }
                }
                skinPairs.add(new SkinPair(attributeName, resId));
            }

        }
        skinViewItem.setSkinAttrs(skinPairs);
        mSkinViewItems.add(skinViewItem);
        if(new File(SkinSpUtils.getInstance().getSkin()).exists()){
            //如果设置了皮肤，而且皮肤存在，就加载皮肤资源
            applySkin();
        }
    }

    /**
     * 换肤操作，遍历所有要换肤的view
     */
    void applySkin() {
        for (SkinViewItem mSkinViewItem : mSkinViewItems) {
            applySkin(mSkinViewItem.getView(), mSkinViewItem.getSkinAttrs());
        }
    }

    void applySkin(View view, ArrayList<SkinPair> skinPairs) {
        for (SkinPair skinPair : skinPairs) {
            String attributeName = skinPair.getAttributeName();
            int resId = skinPair.getResId();
            Drawable left = null, top = null, right = null, bottom = null;
            switch (attributeName) {
                case "background":
                    Object background = SkinResourcesUtils.getInstance().getBackground(
                            resId);
                    //Color
                    if (background instanceof Integer) {
                        view.setBackgroundColor((Integer) background);
                    } else {
                        ViewCompat.setBackground(view, (Drawable) background);
                    }
                    break;
                case "src":
                    background = SkinResourcesUtils.getInstance().getBackground(skinPair
                            .getResId());
                    if (background instanceof Integer) {
                        ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                background));
                    } else {
                        ((ImageView) view).setImageDrawable((Drawable) background);
                    }
                    break;
                case "textColor":
                    ((TextView) view).setTextColor(SkinResourcesUtils.getInstance().getColorStateList
                            (resId));
                    break;
                case "drawableLeft":
                    left = SkinResourcesUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableTop":
                    top = SkinResourcesUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableRight":
                    right = SkinResourcesUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableBottom":
                    bottom = SkinResourcesUtils.getInstance().getDrawable(resId);
                    break;
                default:
                    break;
            }

            if (null != left || null != right || null != top || null != bottom) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                        bottom);
            }
        }
    }
}
