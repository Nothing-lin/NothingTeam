package com.nothinglin.nothingteam.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.dao.ActivityInfoDao;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityDoingFragment;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityDoneFragment;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityWillFragment;
import com.umeng.commonsdk.debug.D;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private List<ActivityInfo> activitydoingInfos = new ArrayList<>();
    private List<ActivityInfo> activitywillInfos = new ArrayList<>();
    private List<ActivityInfo> activitydoneInfos = new ArrayList<>();

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
        activitywillInfos = getActivityInfosThread.activitywillInfos;
        activitydoingInfos = getActivityInfosThread.activitydoingInfos;
        activitydoneInfos = getActivityInfosThread.activitydoneInfos;


        //初始化fragment适配器
        FragmentAdapter<Fragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“活动列表”界面
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        adapter.addFragment(new ActivityDoingFragment(activitydoingInfos),"进行中");

        tabLayout.addTab(tabLayout.newTab().setText("将举办"));
        adapter.addFragment(new ActivityWillFragment(activitywillInfos),"将举办");

        tabLayout.addTab(tabLayout.newTab().setText("已结束"));
        adapter.addFragment(new ActivityDoneFragment(activitydoneInfos),"已结束");



        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

    //创建一个线程来获取mysql数据库中的数据
    public class getActivityInfosThread extends Thread{

        private List<ActivityInfo> activityInfos = new ArrayList<>();

        private List<ActivityInfo> activitydoingInfos = new ArrayList<>();
        private List<ActivityInfo> activitywillInfos = new ArrayList<>();
        private List<ActivityInfo> activitydoneInfos = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            ActivityInfoDao activityInfoDao = new ActivityInfoDao();
            activityInfos = activityInfoDao.getActivityInfosAll();

            for (ActivityInfo item : activityInfos){
                //判断这个活动的举办日期是否发送或已完成
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date activityDate1 = null;

                try {
                    activityDate1 = format.parse(item.getActivityStartTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date activityDate2 = null;
                try {
                    activityDate2 = format.parse(item.getActivityEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date currentDate = Calendar.getInstance().getTime();

                int compareDateResult1 = currentDate.compareTo(activityDate1);
                int compareDateResult2 = currentDate.compareTo(activityDate2);

                if (compareDateResult1 <0){
                    //活动还没发生
                    activitywillInfos.add(item);
                }else if (compareDateResult1 > 0){
                    //活动发生了，但不知道有没结束，所以还要判断
                    if (compareDateResult2 < 0 ){
                        //现在的时间还没超过结束时间，那么活动是正在进行中的
                        activitydoingInfos.add(item);
                    }else {
                        //现在的时间已经超过了活动的结束时间，活动已经结束
                        activitydoneInfos.add(item);
                    }

                }

            }
        }
    }
}
