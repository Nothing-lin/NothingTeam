package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.controller.LoginController;
import com.nothinglin.nothingteam.utils.ClearWriteEditText;

import butterknife.BindView;
import butterknife.Optional;

public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener {


    public ClearWriteEditText mLogin_userName;//用户名输入框
    public ClearWriteEditText mLogin_passWord;//密码输入框
    public Button mBtn_login;//登录按钮
    public TextView mLogin_register;//注册文本，用于替换登录按钮中的“登录”
    public RelativeLayout mTitleBar;//标题栏
    public RelativeLayout mBackground;//背景颜色
    public ImageView mLogin_userLogo;//输入框用户名图标
    public ImageView mLogin_pswLogo;//密码输入框图标
    public View mUserLine;//用户名输入框底部线
    public View mPswLine;//密码输入框底部线
    //登录控制器，控制登录的行为
    public LoginController mLoginController;
    public TextView mNewUser;
    public TextView mLogin_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        initListener();

        mLoginController = new LoginController(this);
        //是否为新用户的点击事件，传到登录控制器中去处理
        mNewUser.setOnClickListener(mLoginController);
        mLogin_register.setOnClickListener(mLoginController);
        mBtn_login.setOnClickListener(mLoginController);


    }

    private void initListener() {
        mLogin_userName.setOnFocusChangeListener(this);
        mLogin_passWord.setOnFocusChangeListener(this);
    }

    private void initViews() {
        mLogin_userName = findViewById(R.id.login_userName);
        mLogin_passWord = findViewById(R.id.login_passWord);
        mLogin_userLogo = findViewById(R.id.login_userLogo);
        mLogin_pswLogo = findViewById(R.id.login_pswLogo);
        mUserLine = findViewById(R.id.user_line);
        mPswLine = findViewById(R.id.psw_line);
        mNewUser = findViewById(R.id.new_user);
        mLogin_desc = findViewById(R.id.login_desc);
        mBtn_login = findViewById(R.id.btn_login);
        mLogin_register = findViewById(R.id.login_register);
    }


    //焦点监听器
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            //用户名输入框焦点监听
            case R.id.login_userName:
                if (hasFocus) {
                    mLogin_userLogo.setImageResource(R.drawable.login_user_press);
                    mUserLine.setBackgroundColor(getResources().getColor(R.color.line_press));
                } else {
                    mLogin_userLogo.setImageResource(R.drawable.login_user_normal);
                    mUserLine.setBackgroundColor(getResources().getColor(R.color.line_normal));
                }
                break;
            case R.id.login_passWord:
                if (hasFocus) {
                    mLogin_pswLogo.setImageResource(R.drawable.login_psw_press);
                    mPswLine.setBackgroundColor(getResources().getColor(R.color.line_press));
                } else {
                    mLogin_pswLogo.setImageResource(R.drawable.login_psw_normal);
                    mPswLine.setBackgroundColor(getResources().getColor(R.color.line_normal));
                }
                break;
        }
    }

    //------------------------------------获取输入框内容方法---------------------------------
    public String getUserId() {
        return mLogin_userName.getText().toString().trim();
    }

    public String getPassword() {
        return mLogin_passWord.getText().toString().trim();
    }
    //-----------------------------------------------------------------------------------
}
