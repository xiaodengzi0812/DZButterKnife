package com.annotations.mybutterknifetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dengzi.annotations.BindView;

/**
 * @Title: 我们自己实现的butterknife的使用demo
 * @Author: djk
 * @Time: 2017/10/31
 * @Version:1.0.0
 */
public class MainActivity extends AppCompatActivity {

    // 这里用的我们自己写的BindView注解
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
//        tv1.setText("tv1");
//        tv2.setText("tv2");
    }
}
