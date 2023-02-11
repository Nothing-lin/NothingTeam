package com.nothinglin.nothingteam.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.dao.ActivityInfoDao;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityDoingFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
@Page(name = "校园活动")
public class ActivityFragment extends BaseFragment {

    //选项标签页面
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        //setImmersive是状态栏的设置，因为一开始已经取消了状态栏的样式了
        super.initTitle().setLeftVisible(false).setImmersive(true);
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initViews() {

        //调用线程
        getActivityInfosThread getActivityInfosThread = new getActivityInfosThread();

        try {
            getActivityInfosThread.start();
            getActivityInfosThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityInfoList = getActivityInfosThread.activityInfos;


        //初始化fragment适配器
        FragmentAdapter<Fragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“系统通告”界面
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        adapter.addFragment(new ActivityDoingFragment(activityInfoList),"进行中");



        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

    //创建一个线程来获取mysql数据库中的数据
    public class getActivityInfosThread extends Thread{

        private List<ActivityInfo> activityInfos = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            ActivityInfoDao activityInfoDao = new ActivityInfoDao();
            activityInfos = activityInfoDao.getActivityInfosAll();
        }
    }
}
