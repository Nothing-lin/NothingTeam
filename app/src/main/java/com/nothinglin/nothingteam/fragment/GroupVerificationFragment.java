package com.nothinglin.nothingteam.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.bean.VerificationReply;
import com.nothinglin.nothingteam.dao.VerificationInfoDao;
import com.nothinglin.nothingteam.fragment.groupverification.GroupVerificationMeFragment;
import com.nothinglin.nothingteam.fragment.groupverification.GroupVerificationOtherFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;

@Page(name = "团队申请审批")
public class GroupVerificationFragment extends BaseFragment {

    //选项标签页面
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<VerificationInfo> mRecommends;
    private List<VerificationReply> MyApplications;


    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        super.initTitle().setLeftVisible(true).setImmersive(true);
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_verification;
    }

    @Override
    protected void initViews() {

        getmRecommendsThread getmRecommendsThread = new getmRecommendsThread();

        try {
            getmRecommendsThread.start();
            getmRecommendsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getMyApplicationThread getMyApplicationThread = new getMyApplicationThread();

        try {
            getMyApplicationThread.start();
            getMyApplicationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mRecommends = getmRecommendsThread.verificationInfos;
        MyApplications = getMyApplicationThread.verificationReplies;


        //初始化fragment适配器
        FragmentAdapter<Fragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //注册适配“我加入的团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("我的审批"));
        adapter.addFragment(new GroupVerificationMeFragment(mRecommends),"我的审批");

        //注册适配“联系团队”界面
        tabLayout.addTab(tabLayout.newTab().setText("审核消息"));
        adapter.addFragment(new GroupVerificationOtherFragment(MyApplications),"审核消息");

        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }


    public class getmRecommendsThread extends Thread{
        private List<VerificationInfo> verificationInfos = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            VerificationInfoDao verificationInfoDao = new VerificationInfoDao();
            String userName = JMessageClient.getMyInfo().getUserName();
            verificationInfos = verificationInfoDao.getAboutManagerData(userName);


        }
    }

    public class getMyApplicationThread extends Thread{
        private List<VerificationReply> verificationReplies = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            VerificationInfoDao verificationInfoDao = new VerificationInfoDao();
            String userName = JMessageClient.getMyInfo().getUserName();
            verificationReplies = verificationInfoDao.getAboutMyApplication(userName);
        }
    }
}
