package com.dengzi.mybutterknife;

import android.support.annotation.UiThread;

/**
 * @Title:
 * @Author: djk
 * @Time: 2017/10/31
 * @Version:1.0.0
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {
        }
    };
}
