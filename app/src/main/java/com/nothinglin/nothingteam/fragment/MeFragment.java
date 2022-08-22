package com.nothinglin.nothingteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.LoginActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
@Page
public class MeFragment extends BaseFragment {

    @BindView(R.id.loginout)
    SuperTextView mloginout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        //setImmersive是状态栏的设置，因为一开始已经取消了状态栏的样式了
        super.initTitle().setLeftVisible(false).setImmersive(true);
        return null;
    }

    @Override
    protected void initViews() {

        mloginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JMessageClient.logout();
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

}
