package com.annotations.dzbutterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dengzi.annotations.BindClick;
import com.dengzi.annotations.BindView;
import com.dengzi.mybutterknife.ButterKnife;
import com.dengzi.mybutterknife.Unbinder;

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

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        userTv1.setText("userTv1");
        userTv2.setText("userTv2");
    }

    @BindClick({R.id.user1, R.id.user2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user1:
                Toast.makeText(UserActivity.this, "userTv1 Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user2:
                Toast.makeText(UserActivity.this, "userTv2 Click", Toast.LENGTH_SHORT).show();
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
