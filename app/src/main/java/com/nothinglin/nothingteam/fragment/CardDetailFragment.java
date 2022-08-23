package com.nothinglin.nothingteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.donkingliang.labels.LabelsView;
import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.CardDetailActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.fragment.carddetail.CardDetailCommentFragment;
import com.nothinglin.nothingteam.fragment.carddetail.ProjectDetailFragment;
import com.nothinglin.nothingteam.fragment.messagepages.ChatToTeamFragment;
import com.nothinglin.nothingteam.fragment.messagepages.SystemMessageFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Page(name = "项目详情页")
public class CardDetailFragment extends BaseFragment {

    public ArrayList<HiresInfos> detailCardInfo = new ArrayList<>();
    public HiresInfos hiresInfos = new HiresInfos();
    
    @BindView(R.id.labels)
    LabelsView labelsView;
    @BindView(R.id.detail_team_name)
    TextView mDetailTeamName;
    @BindView(R.id.detail_school)
    TextView mDetailSchool;
    @BindView(R.id.detail_team_intro)
    TextView mDetailTeamIntro;

    //选项标签页面
    @BindView(R.id.tabSegment)
    TabSegment tabSegment;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_detail;
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        //setImmersive是状态栏的设置，因为一开始已经取消了状态栏的样式了
        super.initTitle().setLeftVisible(true);
        return null;
    }

    @Override
    protected void initViews() {
        

        //获取CardDetailActivity传来的数据
        getdetailCardInfo();
        
        //初始化团队信息栏
        initTeamInfo();

        //初始话（项目详情-留言）标签栏
        initTabSegment();

        //初始化团队技术储备标签
        setTeamLabel();

    }

    private void initTeamInfo() {
        hiresInfos = detailCardInfo.get(0);
        //设置团队信息栏的信息
        mDetailTeamName.setText(hiresInfos.getProject_owner_team_name());
        mDetailSchool.setText(hiresInfos.getTeam_school());
        mDetailTeamIntro.setText(hiresInfos.getTeam_intro());

    }

    private void getdetailCardInfo() {
        Bundle bundle = getArguments();
        detailCardInfo = (ArrayList<HiresInfos>) bundle.getSerializable("detailCardInfo");
        System.out.println(detailCardInfo);
    }

    private void initTabSegment() {

        //初始化fragment适配器
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabSegment.setMode(TabSegment.MODE_FIXED);

        //注册适配“系统通告”界面
        tabSegment.addTab(new TabSegment.Tab("项目详情"));
        adapter.addFragment(new ProjectDetailFragment(),"项目详情");

        //注册适配“联系团队”界面
        tabSegment.addTab(new TabSegment.Tab("留言"));
        adapter.addFragment(new CardDetailCommentFragment(),"留言");

        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabSegment.setupWithViewPager(mViewPager);
    }

    private void setTeamLabel() {

        ArrayList<String> tablist = new ArrayList<>();

//        for (int i =0;i < model.getTabs().size();i++){
//            if (model.getProject_id().equals(model.getTabs().get(i).getProject_id())){
//                tablist.add(model.getTabs().get(i).getAbility_requirements());
//            }
//        }

        for (TeamLabel teamLabel : detailCardInfo.get(0).getTeamLabels()){
            if (teamLabel.getProject_id().equals(detailCardInfo.get(0).getProject_id())){
                tablist.add(teamLabel.getTeam_label());
            }
        }



        labelsView.setLabels(tablist);
        labelsView.setSelectType(LabelsView.SelectType.NONE);
    }
}
