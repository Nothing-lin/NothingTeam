package com.nothinglin.nothingteam.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ChangeNavPageAdapter;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.fragment.ActivityFragment;
import com.nothinglin.nothingteam.fragment.HomeFragment;
import com.nothinglin.nothingteam.fragment.MeFragment;
import com.nothinglin.nothingteam.fragment.TeamFragment;
import com.nothinglin.nothingteam.utils.XToastUtils;
import com.nothinglin.nothingteam.widget.StatusBarUtil;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener, BadgeDismissListener {

    //注册底部菜单栏视图组件==findviewbyid
    @BindView(R.id.tabbar)
    JPTabBar mTabbar;

    private int[] mTitles = {R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4};
    private int[] mSelectIcons = {R.drawable.nav_01_pre, R.drawable.nav_02_pre, R.drawable.nav_04_pre, R.drawable.nav_05_pre};
    private int[] mNormalIcons = {R.drawable.nav_01_nor, R.drawable.nav_02_nor, R.drawable.nav_04_nor, R.drawable.nav_05_nor};

    //底部菜单栏fragment切换
    private List<Fragment> list = new ArrayList<>();
    @BindView(R.id.view_pager)
    ViewPager mPager;

    //注册底部菜单栏的每项fragment界面
    HomeFragment homeFragment;
    MeFragment meFragment;
    ActivityFragment activityFragment;
    TeamFragment teamFragment;

    //-------------------------获取homepage列表信息的声明---------------------------------
    //hiresInfosList装满了全部的募招内容，获取数据库中招募信息的全部数据
    private List<HiresInfos> hiresInfosList = new ArrayList<>();
    //定义募招信息的数据库操作类
    HiresInfosDao hiresInfosDao = new HiresInfosDao();
    //handler是线程信息传递的重要工具，用来接收子线程中的数据
    public Handler handler;
    private List<HiresInfosTabs> hiresInfosTabsList = new ArrayList<>();
    private List<TeamLabel> teamLabelsList = new ArrayList<>();
    //----------------------------------------------------------

    //setContentView(R.layout.activity_main);指向需要展示的界面的布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取homepage列表信息，赋值到hiresInfosList集合中，再传递给homepage
        getHiresInfosList();

        initViews();

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @SuppressLint("ResourceAsColor")
    private void initViews() {
        //给底部导航栏置入文字的图标
        mTabbar.setTitles(mTitles);
        mTabbar.setNormalIcons(mNormalIcons);
        mTabbar.setSelectedIcons(mSelectIcons);
        mTabbar.generate();
        //页面可以滑动
        mTabbar.setGradientEnable(true);
        mTabbar.setPageAnimateEnable(true);
        mTabbar.setTabTypeFace(XUI.getDefaultTypeface());
        mTabbar.setTabListener(this);

        //实例化fragment界面
        homeFragment = new HomeFragment(hiresInfosList);
        meFragment = new MeFragment();
        activityFragment = new ActivityFragment();
        teamFragment = new TeamFragment();

        list.add(homeFragment);
        list.add(teamFragment);
        list.add(activityFragment);
        list.add(meFragment);

        //设置页面适配
        mPager.setAdapter(new ChangeNavPageAdapter(getSupportFragmentManager(), list));
        mTabbar.setContainer(mPager);

        //设置Badge（徽章）消失的代理，刷新icon的作用吧
        mTabbar.setDismissListener(this);
        mTabbar.setTabListener(this);

        //底部导航栏中间按钮的触发事件
        if (mTabbar.getMiddleView() != null) {
            mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, "中间点击", Toast.LENGTH_SHORT).show();
                    showBottomSheetGrid();
                }
            });
        }


        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    private void showBottomSheetGrid(){
        //表格排列的顺序
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;

        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.drawable.createinfo,"发布招聘",TAG_SHARE_WECHAT_FRIEND,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.createteam,"创建团队",TAG_SHARE_WECHAT_MOMENT,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener(((dialog, itemView) -> {
                    //点击之后退出底部弹窗
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    XToastUtils.toast("tag:" + tag + ", content:" + itemView.toString());

                    if (tag == 0){
                        //跳转到招募信息发布页面
                        Intent intent = new Intent();
                        intent.setClass(this,CreateInfosActivity.class);
                        startActivity(intent);
                    }

                })).build().show();


    }


    //实现OnTabSelectListener的接口方法
    @Override
    public void onTabSelect(int index) {
        Toast.makeText(MainActivity.this, "choose the tab index is " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onInterruptSelect(int index) {
        //  if(index==2){
        //   如果这里有需要阻止Tab被选中的话,可以return true
        //      return true;
        //  }
        return false;
    }

    //BadgeDismissListener的接口实现
    @Override
    public void onDismiss(int position) {

    }


    private void getHiresInfosList(){

        //更新子线程 -- 用join实现，主线程等待子线程完成之后再执行主线程之后的步骤
        Thread getHiresInfosListThread = new getHiresInfosListThread();
        try {
            getHiresInfosListThread.start();
            getHiresInfosListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public class getHiresInfosListThread extends Thread{
        @Override
        public void run() {
            super.run();

            //调用数据库操作类方法
            hiresInfosList = hiresInfosDao.getHiresInfoAll();
            hiresInfosTabsList = hiresInfosDao.getHiresInfoTabsAll();
            teamLabelsList = hiresInfosDao.getTeamLabelsAll();

            //将标签送入信息列表中，这样后面ToolTabCardListFragment --> CardViewListAdapter 循环传值的时候才方便
            for (HiresInfos info : hiresInfosList){
                info.setTabs(hiresInfosTabsList);
            }

            for (HiresInfos info : hiresInfosList){
                info.setTeamLabels(teamLabelsList);
            }

        }
    }
}