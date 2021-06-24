package com.gyzq.skin;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyzq.skin.language.LanguageManager;
import com.gyzq.skin.utils.SkinResUtils;
import com.gyzq.skin.utils.SkinSpUtils;
import com.gyzq.skin.utils.SkinThemeUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author: liujie
 * @date: 2021/6/7
 * @description: 皮肤属性类，解析所有view的属性，保存起来
 */
public class SkinAttribute {

    //用于保存view和view的各个属性的集合
    private final ArrayList<SkinViewItem> mSkinViewItems;

    public SkinAttribute() {
        this.mSkinViewItems = new ArrayList<>();
    }

    //解析属性集合，保存到list中
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
                //如果是以#开头的资源，是写死的，就不支持换肤，不需要处理 || attributeValue.startsWith("?")
                if (attributeValue.startsWith("#")) {
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
                //判断前缀字符串 是否是"?"
                if (attributeValue.startsWith("?")) {
                    //系统属性值,比如：android:textColor="?colorAccent"，则属性ID的名称是从属性值下标1的位置开始，注意：它在R中是一个int类型的
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                }
                skinPairs.add(new SkinPair(attributeName, resId));
            }

        }
        skinViewItem.setSkinAttrs(skinPairs);
        mSkinViewItems.add(skinViewItem);
        //后期优化这里
        if (new File(SkinSpUtils.getInstance().getSkin()).exists()) {
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

    /**
     * 换字体操作，遍历所有textview，设置新的字体
     */
    void applyTypeFace() {
        Typeface typeface = SkinResUtils.getInstance().getTypeface();
        for (SkinViewItem mSkinViewItem : mSkinViewItems) {
            View view = mSkinViewItem.getView();
            if (view instanceof TextView) {
                //是textview就换字体
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    /**
     * 切换语言
     */
    void applyLanguage() {
        Context context = null;
        for (SkinViewItem mSkinViewItem : mSkinViewItems) {
            View view = mSkinViewItem.getView();
            if (view instanceof TextView) {
                if (null == context) {
                    context = view.getContext();
                    LanguageManager.getInstance().attachBaseContext(context);
                }
                //设置resource的config
                ArrayList<SkinPair> skinPairs = mSkinViewItem.getSkinAttrs();
                int size = skinPairs.size();
                for (int i = 0; i < size; i++) {
                    int id = skinPairs.get(i).getResId();
                    if (id != 0) {
                        if (TextUtils.equals("text", skinPairs.get(i).getAttributeName())) {
                            //是textview就换字体
                            TextView textView = (TextView) view;
                            textView.setTextLocale(LanguageManager.getPreferredLocale());
                            textView.setText(id);
                        } else if (TextUtils.equals("hint", skinPairs.get(i).getAttributeName())) {
                            EditText editText = (EditText) view;
                            editText.setTextLocale(LanguageManager.getPreferredLocale());
                            editText.setHint(id);
                        }
                    }

                }
            }
        }
    }

    void applySkin(View view, ArrayList<SkinPair> skinPairs) {
        for (SkinPair skinPair : skinPairs) {
            String attributeName = skinPair.getAttributeName();
            int resId = skinPair.getResId();
            Drawable left = null, top = null, right = null, bottom = null;
            switch (attributeName) {
                case "background":
                    Object background = SkinResUtils.getInstance().getBackground(
                            resId);
                    //Color
                    if (background instanceof Integer) {
                        view.setBackgroundColor((Integer) background);
                    } else {
                        ViewCompat.setBackground(view, (Drawable) background);
                    }
                    break;
                case "src":
                    background = SkinResUtils.getInstance().getBackground(skinPair
                            .getResId());
                    if (background instanceof Integer) {
                        ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                background));
                    } else {
                        ((ImageView) view).setImageDrawable((Drawable) background);
                    }
                    break;
                case "textColor":
                    ((TextView) view).setTextColor(SkinResUtils.getInstance().getColorStateList
                            (resId));
                    break;
                case "drawableLeft":
                case "drawableStart":
                    left = SkinResUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableTop":
                    top = SkinResUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableRight":
                case "drawableEnd":
                    right = SkinResUtils.getInstance().getDrawable(resId);
                    break;
                case "drawableBottom":
                    bottom = SkinResUtils.getInstance().getDrawable(resId);
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

    void destroyView() {
        mSkinViewItems.clear();
    }

}
