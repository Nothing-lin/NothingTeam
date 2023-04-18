package com.nothinglin.nothingteam.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.fragment.annoucement.SystemTipsFragment;
import com.nothinglin.nothingteam.fragment.commespages.ActivityCommentFragment;
import com.nothinglin.nothingteam.fragment.commespages.ProjectCommentFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;

import butterknife.BindView;
@Page(name = "系统消息")
public class AnnoucementFragment extends BaseFragment {
    //选项标签页面
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_annoucement_list;
    }

    @Override
    protected void initViews() {
        //初始化fragment适配器
        FragmentAdapter<Fragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“我加入的团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("系统公告"));
        adapter.addFragment(new SystemTipsFragment(),"系统公告");

        //注册适配“联系团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("申请回复"));
        adapter.addFragment(new ActivityCommentFragment(),"申请回复");

        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
