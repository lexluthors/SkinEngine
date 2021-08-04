package com.gyzq.skin;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description: view创建类
 */
public class SkinViewFactory {
    private static final Object[] CONSTRUCTOR_ARGS = new Object[2];
    private static final Map<String, Constructor<? extends View>> CONSTRUCTOR_MAP
            = new ArrayMap<>();
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] CLASS_PREFIX_LIST = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };

    static View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if ("view".equals(name)) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            CONSTRUCTOR_ARGS[0] = context;
            CONSTRUCTOR_ARGS[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (String s : CLASS_PREFIX_LIST) {
                    final View view = createView(context, name, s);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // 返回空，使用系统自己的布局加载器创建view
            return null;
        } finally {
            // 清空
            CONSTRUCTOR_ARGS[0] = null;
            CONSTRUCTOR_ARGS[1] = null;
        }
    }

    private static View createView(Context context, String name, String prefix) {
        Constructor<? extends View> constructor = CONSTRUCTOR_MAP.get(name);
        try {
            if (constructor == null) {
                // 反射创建view
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(CONSTRUCTOR_SIGNATURE);
                CONSTRUCTOR_MAP.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(CONSTRUCTOR_ARGS);
        } catch (Exception e) {
            // 如果创建失败，返回空，让系统去处理，系统会自动创建view
            return null;
        }
    }
}
