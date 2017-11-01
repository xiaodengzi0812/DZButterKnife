package com.dengzi.mybutterknife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Title:
 * @Author: djk
 * @Time: 2017/10/31
 * @Version:1.0.0
 */
public class Utils {
    public static final <T extends View> T findViewById(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }
}
