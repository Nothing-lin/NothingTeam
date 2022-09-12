package com.nothinglin.nothingteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.donkingliang.labels.LabelsView;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.SingleChatActivity;
import com.nothinglin.nothingteam.adapter.CommentExpandAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.CommentBean;
import com.nothinglin.nothingteam.bean.CommentDetailBean;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.widget.CommentExpandableListView;
import com.nothinglin.nothingteam.widget.DemoDataProvider;
import com.nothinglin.nothingteam.widget.RadiusImageBanner;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jmessage.support.google.gson.Gson;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

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
    @BindView(R.id.bt_chat_to_team)
    Button mChatToTeamButtom;
    @BindView(R.id.competiton_goal)
    TextView mCompetitonGoal;
    @BindView(R.id.detail_project)
    TextView mProjectIntroduction;
    @BindView(R.id.hire_detail)
    TextView mHireDetail;


    @BindView(R.id.rib_simple_usage)
    RadiusImageBanner rib_simple_usage;
    //mPictures是用来存放轮播图图片的
    private List<BannerItem> mPictures;

    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private String testJson = new DemoDataProvider().testJson;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_detail;
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        //获取CardDetailActivity传来的数据
        getdetailCardInfo();
        //setImmersive是状态栏的设置，因为一开始已经取消了状态栏的样式了
        super.initTitle().setLeftVisible(true).setTitle("项目详情");
        return null;
    }

    @Override
    protected void initViews() {
        

        //获取CardDetailActivity传来的数据
        getdetailCardInfo();
        
        //初始化团队信息栏
        initTeamInfo();

        //轮播图
        mPictures = DemoDataProvider.getBannerList();
        sib_simple_usage();


        //初始化团队技术储备标签
        setTeamLabel();

        //初始化监听器
        initListener();


        //初始化评论模块
        initComments();

    }

    private void initComments() {
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        commentsList = generateTestData();
        initExpandableListView(commentsList);
    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(getContext(), commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }


    }



    private void sib_simple_usage() {
        rib_simple_usage.setSource(mPictures).startScroll();
    }

    private void initListener() {
        mChatToTeamButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                JMessageClient.getUserInfo(detailCardInfo.get(0).getTeam_manager_userid(), null, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0){
                            intent.putExtra("teamUserId",detailCardInfo.get(0).getTeam_manager_userid());
                            intent.putExtra("teamUserName",userInfo.getUserName());
                            intent.setClass(getActivity(), SingleChatActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });
    }

    private void initTeamInfo() {
        hiresInfos = detailCardInfo.get(0);
        //设置团队信息栏的信息
        mDetailTeamName.setText(hiresInfos.getProject_owner_team_name());
        mDetailSchool.setText(hiresInfos.getTeam_school());
        mDetailTeamIntro.setText(hiresInfos.getTeam_intro());
        mCompetitonGoal.setText(hiresInfos.getCompetition_type());
        mProjectIntroduction.setText(hiresInfos.getProject_detail());
        mHireDetail.setText(hiresInfos.getHire_detail());

        new TitleBar(getContext()).setTitle(hiresInfos.getProject_name());

    }

    private void getdetailCardInfo() {
        Bundle bundle = getArguments();
        detailCardInfo = (ArrayList<HiresInfos>) bundle.getSerializable("detailCardInfo");
        System.out.println(detailCardInfo);
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
