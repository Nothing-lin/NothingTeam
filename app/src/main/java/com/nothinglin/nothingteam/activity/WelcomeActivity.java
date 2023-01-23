package com.nothinglin.nothingteam.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nothinglin.nothingteam.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * app启动页面
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //启动界面延迟2秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initAfterWelcome();
            }
        },2000);

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    private void initAfterWelcome() {
        //判断用户是否已经登录过了，如果登陆过了就跳转到main界面，如果没有登录记录的话就跳转回登录界面
        //注意这个userinfo是极光的bean，不是我们自己定义的那个
        UserInfo userInfo = JMessageClient.getMyInfo();

        if (userInfo == null){
            //如果极光服务器没有获取到登录记录的话就重新登录
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else {
            //如果已经登录过了就直接回到主界面
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
