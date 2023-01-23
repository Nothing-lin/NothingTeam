package com.nothinglin.nothingteam.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.donkingliang.labels.LabelsView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.activity.SingleChatActivity;
import com.nothinglin.nothingteam.adapter.CommentExpandAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.CommentBean;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.bean.CommentDetailBean;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.dao.DetailCommentDao;
import com.nothinglin.nothingteam.widget.CommentExpandableListView;
import com.nothinglin.nothingteam.widget.DemoDataProvider;
import com.nothinglin.nothingteam.widget.RadiusImageBanner;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jmessage.support.google.gson.Gson;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

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
    @BindView(R.id.project_name)
    TextView mProjecName;
    @BindView(R.id.detail_page_do_comment)
    TextView mBt_comment;
    @BindView(R.id.bt_group_apply)
    Button mBtGroupApply;


    @BindView(R.id.rib_simple_usage)
    RadiusImageBanner rib_simple_usage;
    //mPictures是用来存放轮播图图片的
    private List<BannerItem> mPictures;

    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private List<CommentDetail> AllCommentsList = new ArrayList<>();
    private List<CommentDetail> commentsList = new ArrayList<>();
    private String testJson = new DemoDataProvider().testJson;
    private BottomSheetDialog dialog;



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
        try {
            //初始化评论区的数据
            generateData();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initExpandableListView(commentsList);

    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     *
     * @return 评论数据
     */
    private void generateData() throws InterruptedException {

        MyThread myThread = new MyThread();
        myThread.start();
        myThread.join();


    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetail> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(getContext(), commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
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
                        if (i == 0) {
                            intent.putExtra("teamUserId", detailCardInfo.get(0).getTeam_manager_userid());
                            intent.putExtra("teamUserName", userInfo.getUserName());
                            intent.setClass(getActivity(), SingleChatActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

        //弹出回复编辑窗口
        mBt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化底部弹窗内容
                dialog = new BottomSheetDialog(getContext());
                View commentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail_comment_dialog_layout, null);
                final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
                final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
                dialog.setContentView(commentView);
                dialog.show();

                //确定评论
                bt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentContent = commentText.getText().toString().trim();
                        CommentDetail testDetail = new CommentDetail();
                        testDetail.setUser_name("Nothinglin");
                        testDetail.setComment_content(commentContent);

                        Thread insertCommentThread = new InsetCommentThread(hiresInfos.getProject_id(),"Nothinglin",commentContent);

                        try {

                            insertCommentThread.start();
                            insertCommentThread.join();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        commentsList.add(testDetail);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });

            }
        });

        //申请入群带输入框的弹窗
        mBtGroupApply.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_cat).titleColor(Color.BLACK).backgroundColor(Color.WHITE)
                        .title("入群申请")
                        .content("请输入您的入群申请原因")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入您的申请入群请求", "", true, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                //发送加入群组验证消息
                                String verification = dialog.getInputEditText().getText().toString();

                                JMessageClient.applyJoinGroup(75514999, verification, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        //发送群申请成功 、发送的信息到极光服务器中进行处理
                                        if (i == 0){
                                            Toast.makeText(getActivity(), "申请已发出,等待审核", Toast.LENGTH_SHORT).show();
                                            getActivity().finish();
                                        }else {
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "您已是该群成员，请勿再次申请！", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        })
                        .positiveText("确定申请")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                Toast.makeText(getActivity(), "这是onPositive触发的弹窗:" + dialog.getInputEditText().getText().toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .cancelable(false)
                        .show();
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
        mProjecName.setText(hiresInfos.getProject_name());

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

        for (TeamLabel teamLabel : detailCardInfo.get(0).getTeamLabels()) {
            if (teamLabel.getProject_id().equals(detailCardInfo.get(0).getProject_id())) {
                tablist.add(teamLabel.getTeam_label());
            }
        }


        labelsView.setLabels(tablist);
        labelsView.setSelectType(LabelsView.SelectType.NONE);
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
            AllCommentsList = new DetailCommentDao().getAllComment();

            for (CommentDetail commentDetail : AllCommentsList){
                if (commentDetail.getProject_id().equals(hiresInfos.getProject_id())){
                    commentsList.add(commentDetail);
                }
            }
        }
    }

    public class InsetCommentThread extends Thread{
        private String project_id;
        private String user_name;
        private String content;

        InsetCommentThread(String project_id,String user_name,String content){
            this.project_id = project_id;
            this.user_name = user_name;
            this.content = content;
        }

        @Override
        public void run() {

            new DetailCommentDao().InsetComment(project_id,user_name,null,content);

        }
    }
}
