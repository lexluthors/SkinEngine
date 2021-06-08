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

import java.util.Observable;
import java.util.Observer;

/**
 * @author: liujie
 * @date: 2021/6/7 上午9:19 
 * @description: 
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
        if(mActivity instanceof AppCompatActivity){
            AppCompatDelegate delegate = ((AppCompatActivity)mActivity).getDelegate();
            view = delegate.createView(parent, name, context, attrs);
        }
        if (view == null) {
            view = SkinViewFactory.createViewFromTag(context, name, attrs);
        }
        skinAttribute.parseSkinAttr(view,attrs);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (skinAttribute!=null){
            skinAttribute.applySkin();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }
}
