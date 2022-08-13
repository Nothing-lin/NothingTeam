package com.nothinglin.nothingteam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import butterknife.BindView;

/**
 * 团队页面，运用选项标签页面，一个页面是我加入的其他人创建的团队群，另一个是我自己创建的聊天群
 */
//标题栏的标题
@Page(name = "团队交流")
public class TeamFragment extends BaseFragment {

    //选项标签页面
    @BindView(R.id.easy_indicator)
    EasyIndicator mEasyIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected TitleBar initTitle() {
        super.initTitle().setLeftVisible(false).setImmersive(true);


        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
    }

    @Override
    protected void initViews() {

    }

}
