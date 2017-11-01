package com.dengzi.mybutterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * @Title:
 * @Author: djk
 * @Time: 2017/10/31
 * @Version:1.0.0
 */
public class ButterKnife {
    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    /**
     * 绑定view，编译时注解中用到唯一的反射的地方
     *
     * @param activity
     * @return
     */
    public final static Unbinder bind(Activity activity) {
        // 相当于我们在这里手动调用 new MainActivity_ViewBinding(activity); 方法
        // 在编译生成的 MainActivity_ViewBinding 类中对view做了findViewById

        // 获取我们编译时生成的class类，类名+_ViewBinding，在编译时也是这么写死的
        String viewBindingClassName = activity.getClass().getName() + "_ViewBinding";
        try {
            // 获取到class类
            Class<? extends Unbinder> viewBindingClass = (Class<? extends Unbinder>) Class.forName(viewBindingClassName);
            // 获取到构造方法
            Constructor<? extends Unbinder> viewBindingConstructor = viewBindingClass.getDeclaredConstructor(activity.getClass());
            // 创建类
            Unbinder unbinder = viewBindingConstructor.newInstance(activity);
            return unbinder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Unbinder.EMPTY;
    }
}
