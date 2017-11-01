package com.annotations.mybutterknifetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dengzi.annotations.BindView;

/**
 * @Title: butterknife的基本使用
 * @Author: djk
 * @Time: 2017/10/31
 * @Version:1.0.0
 */
public class UserActivity extends AppCompatActivity {
    @BindView(R.id.user1)
    TextView userTv1;
    @BindView(R.id.user2)
    TextView userTv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
    }


    private void initView() {
//        userTv1.setText("tv1");
//        userTv2.setText("tv2");
    }

}
