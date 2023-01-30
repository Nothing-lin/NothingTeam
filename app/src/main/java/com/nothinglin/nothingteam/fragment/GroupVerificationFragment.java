package com.nothinglin.nothingteam.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.fragment.groupverification.GroupVerificationMeFragment;
import com.nothinglin.nothingteam.fragment.groupverification.GroupVerificationOtherFragment;
import com.nothinglin.nothingteam.fragment.teampages.ChatToTeamFragment;
import com.nothinglin.nothingteam.fragment.teampages.JoinTeamFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import butterknife.BindView;

@Page(name = "团队申请审批")
public class GroupVerificationFragment extends BaseFragment {

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_verification;
    }

    @Override
    protected void initViews() {

        //初始化fragment适配器
        FragmentAdapter<Fragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“我加入的团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("我的审批"));
        adapter.addFragment(new GroupVerificationMeFragment(),"我的审批");

        //注册适配“联系团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("我的入群"));
        adapter.addFragment(new GroupVerificationOtherFragment(),"我的入群");

    }
}
