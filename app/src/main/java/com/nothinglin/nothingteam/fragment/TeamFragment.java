package com.nothinglin.nothingteam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.fragment.teampages.JoinTeamFragment;
import com.nothinglin.nothingteam.fragment.teampages.MyTeamFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
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
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        super.initTitle().setLeftVisible(false).setImmersive(true);
        return null;
    }

    //获取布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
    }



    @Override
    protected void initViews() {
        //初始化fragment适配器
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“我加入的团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("我加入的团队"));
        adapter.addFragment(new JoinTeamFragment(),"我加入的团队");
        //注册适配“我创建的团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("我创建的团队"));
        adapter.addFragment(new MyTeamFragment(),"我创建的团队");

        //设置团队页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

}
