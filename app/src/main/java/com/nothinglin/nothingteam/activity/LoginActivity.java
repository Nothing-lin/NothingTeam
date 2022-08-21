package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.utils.ClearWriteEditText;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_userName)
    ClearWriteEditText mLogin_userName;//用户名输入框
    @BindView(R.id.login_passWord)
    ClearWriteEditText mLogin_passWord;//密码输入框
    @BindView(R.id.btn_login)
    Button mBtn_login;//登录按钮
    @BindView(R.id.login_register)
    TextView mLogin_register;//注册文本，用于替换登录按钮中的“登录”
    @BindView(R.id.titlebar)
    RelativeLayout mTitleBar;//标题栏
    @BindView(R.id.background)
    RelativeLayout mBackground;//背景颜色




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }


}
