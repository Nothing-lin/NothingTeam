package com.nothinglin.nothingteam.fragment;

import android.content.Intent;
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

@Page(name = "???????????????")
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
    //mPictures?????????????????????????????????
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

    //??????????????????
    @Override
    protected TitleBar initTitle() {
        //??????CardDetailActivity???????????????
        getdetailCardInfo();
        //setImmersive???????????????????????????????????????????????????????????????????????????
        super.initTitle().setLeftVisible(true).setTitle("????????????");
        return null;
    }

    @Override
    protected void initViews() {


        //??????CardDetailActivity???????????????
        getdetailCardInfo();

        //????????????????????????
        initTeamInfo();

        //?????????
        mPictures = DemoDataProvider.getBannerList();
        sib_simple_usage();


        //?????????????????????????????????
        setTeamLabel();

        //??????????????????
        initListener();


        //?????????????????????
        initComments();

    }

    private void initComments() {
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        try {
            //???????????????????????????
            generateData();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initExpandableListView(commentsList);

    }

    /**
     * by moos on 2018/04/20
     * func:??????????????????
     *
     * @return ????????????
     */
    private void generateData() throws InterruptedException {

        MyThread myThread = new MyThread();
        myThread.start();
        myThread.join();


    }

    /**
     * ??????????????????????????????
     */
    private void initExpandableListView(final List<CommentDetail> commentList) {
        expandableListView.setGroupIndicator(null);
        //????????????????????????
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

        //????????????????????????
        mBt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????
                dialog = new BottomSheetDialog(getContext());
                View commentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail_comment_dialog_layout, null);
                final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
                final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
                dialog.setContentView(commentView);
                dialog.show();

                //????????????
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

        //?????????????????????????????????
        mBtGroupApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .iconRes(R.drawable.ic_cat)
                        .title("????????????")
                        .content("?????????????????????????????????")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("?????????????????????????????????", "", true, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                //??????????????????????????????
                                String verification = dialog.getInputEditText().getText().toString();

                                JMessageClient.applyJoinGroup(75514999, verification, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        //????????????????????? ???????????????????????????????????????????????????
                                        if (i == 0){
                                            Toast.makeText(getActivity(), "???????????????,????????????", Toast.LENGTH_SHORT).show();
                                            getActivity().finish();
                                        }else {
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        })
                        .positiveText("????????????")
                        .negativeText("??????")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                Toast.makeText(getActivity(), "??????onPositive???????????????:" + dialog.getInputEditText().getText().toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .cancelable(false)
                        .show();
            }
        });

    }

    private void initTeamInfo() {
        hiresInfos = detailCardInfo.get(0);
        //??????????????????????????????
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
