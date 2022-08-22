package com.nothinglin.nothingteam.controller;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.LoginActivity;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.dao.UserInfoDao;
import com.nothinglin.nothingteam.utils.GlobalThreadPool;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginController implements View.OnClickListener {

    //获取LoginActivity的文本到本控制器中
    private LoginActivity mContext;
    //判断对应状态为登录还是注册,默认奇数是登录状态，偶数是注册状态
    public static long registerOrLogin = 1;

    private UserInfoDao userInfoDao = new UserInfoDao();

    public LoginController(LoginActivity mContext) {
        this.mContext = mContext;
    }

    private static int isRegisterSuccess = 1;

    //点击事件的处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //登录-注册状态切换
            case R.id.login_register:
            case R.id.new_user:
                //先清空输入框中的值
                mContext.mLogin_userName.setText("");
                mContext.mLogin_passWord.setText("");
                registerOrLogin++;
                if (registerOrLogin % 2 == 0) {
                    mContext.mBtn_login.setText("注册");
                    mContext.mNewUser.setText("去登陆");
                    mContext.mLogin_register.setText("立即登陆");
                    mContext.mLogin_desc.setText("已有账号? ");
                } else {
                    mContext.mBtn_login.setText("登录");
                    mContext.mNewUser.setText("新用户");
                    mContext.mLogin_register.setText("立即注册");
                    mContext.mLogin_desc.setText("还没有账号? ");
                }
                break;

            case R.id.btn_login:
                //获取输入框信息
                final String userId = mContext.getUserId();
                final String password = mContext.getPassword();
                //---------------------判断输入的内容是否符合条件----------------------------------
                if (TextUtils.isEmpty(userId)) {
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "用户名不能为空", "确定");
                    mContext.mLogin_userName.setShakeAnimation();//提示的地方进行动画抖动
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "密码不能为空", "确定");
                    mContext.mLogin_passWord.setShakeAnimation();
                    return;
                }
                if (userId.length() < 4 || userId.length() > 128) {
                    mContext.mLogin_userName.setShakeAnimation();
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "用户名为4-128位字符", "确定");
                    return;
                }
                if (password.length() < 4 || password.length() > 128) {
                    mContext.mLogin_userName.setShakeAnimation();
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "密码为4-128位字符", "确定");
                    return;
                }
                if (isContainChinese(userId)) {
                    mContext.mLogin_userName.setShakeAnimation();
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "用户名不支持中文", "确定");
                    return;
                }
                if (!whatStartWith(userId)) {
                    mContext.mLogin_userName.setShakeAnimation();
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "用户名以字母或者数字开头", "确定");
                    return;
                }
                if (!whatContain(userId)) {
                    mContext.mLogin_userName.setShakeAnimation();
                    DialogLoader.getInstance().showTipDialog(mContext, "提示", "只能含有: 数字 字母 下划线 . - @", "确定");
                    return;
                }
                //-----------------------------------------------------------------

                //登录
                if (registerOrLogin % 2 == 1) {
                    JMessageClient.login(userId, password, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {

                            //如果登陆成功
                            if (i == 0) {
                                DialogLoader.getInstance().showTipDialog(mContext, "提示", "登陆成功", "");
                                Intent intent = new Intent();
                                intent.setClass(mContext, MainActivity.class);
                                mContext.startActivity(intent);
                                mContext.finish();
                            } else {
                                DialogLoader.getInstance().showTipDialog(mContext, "提示", "登录失败:" + s, "确定");
                            }
                        }
                    });
                } else {

                    //连接mysql必须开线程
                    GlobalThreadPool.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {

                            //注册
                            JMessageClient.register(userId, password, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {

                                        isRegisterSuccess = 1;
                                        DialogLoader.getInstance().showTipDialog(mContext, "提示", "注册成功:", "确定");

                                    } else {
                                        isRegisterSuccess = 0;
                                        DialogLoader.getInstance().showTipDialog(mContext, "提示", "注册失败:" + s, "确定");
                                    }
                                }
                            });
                            if (isRegisterSuccess == 1) {
                                userInfoDao.setUserInfo(userId, password);
                            }

                        }
                    });

                }

        }
    }

    //------------------------------------判断工具方法---------------------------------------
    //判断输入框是否含有中文
    private boolean isContainChinese(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    //判断用户名是否以字母或数字开头
    private boolean whatStartWith(String str) {
        Pattern pattern = Pattern.compile("^([A-Za-z]|[0-9])");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    //判断用户名是否只含有 数字 字母 下划线 . - @这些元素
    private boolean whatContain(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z][a-zA-Z0-9_\\-@\\.]{3,127}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    //-----------------------------------------------------------------------------------
}
