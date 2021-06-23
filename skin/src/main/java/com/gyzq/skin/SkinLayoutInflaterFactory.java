package com.gyzq.skin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: liujie
 * @date: 2021/6/7 上午9:19
 * @description: 自定义Factory2，替换系统的view创建
 */
public class SkinLayoutInflaterFactory implements LayoutInflater.Factory2, Observer {

    Activity mActivity;
    SkinAttribute skinAttribute;

    public SkinLayoutInflaterFactory(Activity activity) {
        mActivity = activity;
        skinAttribute = new SkinAttribute();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = null;

        if (mActivity instanceof AppCompatActivity) {
            AppCompatDelegate delegate = ((AppCompatActivity) mActivity).getDelegate();
            view = delegate.createView(parent, name, context, attrs);
        }
        if (view == null) {
            switch (name) {
                case "TextView":
                    view = new TextView(context, attrs);
                    break;
                case "ImageView":
                    view = new ImageView(context, attrs);
                    break;
                case "LinearLayout":
                    view = new LinearLayout(context, attrs);
                    break;
                case "RelativeLayout":
                    view = new RelativeLayout(context, attrs);
                    break;
                case "Button":
                    view = new Button(context, attrs);
                    break;
                case "ListView":
                    view = new ListView(context, attrs);
                    break;
                case "RadioButton":
                    view = new RadioButton(context, attrs);
                    break;
                case "FrameLayout":
                    view = new FrameLayout(context, attrs);
                    break;
//                case "android.support.v4.app.FragmentTabHost":
//                    view = new FragmentTabHost(context,attrs);
//                    break;
            }
        }
        if (view == null) {
            view = SkinViewFactory.createViewFromTag(context, name, attrs);
        }
        if (view == null) {
            return null;
        }
        skinAttribute.parseSkinAttr(view, attrs);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (SkinType.SKIN == arg) {
            System.out.println("皮肤换肤");
            if (skinAttribute != null) {
                skinAttribute.applySkin();
                System.out.println("结束时间》》》》"+System.currentTimeMillis());
            }
        } else if (SkinType.TYPEFACE == arg) {
            System.out.println("换字体");
            skinAttribute.applyTypeFace();
        } else if (SkinType.LANGUAGE ==arg){
            skinAttribute.applyLanguage();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    void destroyView(){
        skinAttribute.destroyView();
    }
}
