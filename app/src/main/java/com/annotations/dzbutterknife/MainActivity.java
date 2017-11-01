package com.annotations.dzbutterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dengzi.annotations.BindClick;
import com.dengzi.annotations.BindView;
import com.dengzi.mybutterknife.ButterKnife;
import com.dengzi.mybutterknife.Unbinder;

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

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv1.setText("tv1");
        tv2.setText("tv2");
    }

    @BindClick(R.id.tv1)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // 在销毁的时候调用unbind
        mUnbinder.unbind();
        super.onDestroy();
    }

}
