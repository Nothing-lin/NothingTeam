package com.nothinglin.nothingteam.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.DetailPicture;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.util.ArrayList;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "项目详情编辑页")
public class CardDetailEditFragment extends BaseFragment {

    @BindView(R.id.project_name)
    ClearEditText getProjectName;

    @BindView(R.id.project_owner_team_name)
    ClearEditText getTeamName;

    @BindView(R.id.project_type)
    ClearEditText getProjectType;

    @BindView(R.id.competition_type)
    ClearEditText getCompetitionType;

    @BindView(R.id.hire_numbers)
    ClearEditText getHireNumbers;

    @BindView(R.id.team_school)
    ClearEditText getTeamSchool;

    @BindView(R.id.project_pisiton)
    ClearEditText getProjectposition;

    @BindView(R.id.project_introduction)
    MultiLineEditText getProjectIntroduction;

    @BindView(R.id.project_detail)
    MultiLineEditText getProjectDetail;

    @BindView(R.id.team_intro)
    MultiLineEditText getTeamIntro;

    @BindView(R.id.hire_detail)
    MultiLineEditText getHireDetail;

    @BindView(R.id.create_project)
    Button createProject;

    @BindView(R.id.team_avatar)
    ImageView getTeamAvatar;

    //简介图
    @BindView(R.id.detail_picture1)
    ImageView getDetailPicture1;
    DetailPicture detailPicture1 = new DetailPicture();

    @BindView(R.id.detail_picture2)
    ImageView getDetailPicture2;
    DetailPicture detailPicture2 = new DetailPicture();

    @BindView(R.id.detail_picture3)
    ImageView getDetailPicture3;
    DetailPicture detailPicture3 = new DetailPicture();

    @BindView(R.id.project_requirement)
    ClearEditText getProjectRequirement;

    @BindView(R.id.team_label)
    ClearEditText getTeamLabel;

    @BindView(R.id.detail_picture)
    TextView getPictureTitle;

    public ArrayList<HiresInfos> detailCardInfo = new ArrayList<>();
    public HiresInfos hiresInfos = new HiresInfos();



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_createinfo;
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {

        TitleBar titleBar = super.initTitle();
        titleBar.setLeftVisible(true).setTitle("项目详情编辑");

        return null;
    }

    @Override
    protected void initViews() {

        Bundle bundle = getArguments();
        detailCardInfo = (ArrayList<HiresInfos>) bundle.getSerializable("detailCardInfo");
        hiresInfos = detailCardInfo.get(0);

        getProjectName.setText(hiresInfos.getProject_name());
        getTeamName.setText(hiresInfos.getProject_owner_team_name());

        byte[] imageBytes = Base64.decode(hiresInfos.getTeam_avatar(), Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        getTeamAvatar.setImageBitmap(decodeImage);

        getProjectType.setText(hiresInfos.getProject_type());
        getCompetitionType.setText(hiresInfos.getCompetition_type());
        getHireNumbers.setText(hiresInfos.getHire_numbers());
        getTeamSchool.setText(hiresInfos.getTeam_school());
        getProjectposition.setText(hiresInfos.getProject_position());

        getProjectIntroduction.setContentText(hiresInfos.getProject_introdution());
        getProjectDetail.setContentText(hiresInfos.getProject_detail());
        getHireDetail.setContentText(hiresInfos.getHire_detail());
        getTeamIntro.setContentText(hiresInfos.getTeam_intro());

        getTeamLabel.setText("JAVA|PHP|JAVASCRIPT");
        getProjectRequirement.setText("文档编辑|后端开发|前端开发");

        getPictureTitle.setVisibility(View.GONE);
        getDetailPicture1.setVisibility(View.GONE);
        getDetailPicture2.setVisibility(View.GONE);
        getDetailPicture3.setVisibility(View.GONE);


        createProject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                HiresInfos newhiresInfos = new HiresInfos();
                newhiresInfos.setProject_name(String.valueOf(getProjectName.getText()));
                newhiresInfos.setProject_owner_team_name(String.valueOf(getTeamName.getText()));
                newhiresInfos.setTeam_avatar(hiresInfos.getTeam_avatar());
                newhiresInfos.setProject_type(String.valueOf(getProjectType.getText()));
                newhiresInfos.setCompetition_type(String.valueOf(getCompetitionType.getText()));
                newhiresInfos.setHire_numbers(String.valueOf(getHireNumbers.getText()));
                newhiresInfos.setTeam_school(String.valueOf(getTeamSchool.getText()));
                newhiresInfos.setProject_position(String.valueOf(getProjectposition.getText()));
                newhiresInfos.setProject_introdution(String.valueOf(getProjectIntroduction.getContentText()));
                newhiresInfos.setProject_detail(String.valueOf(getProjectDetail.getContentText()));
                newhiresInfos.setHire_detail(String.valueOf(getHireDetail.getContentText()));
                newhiresInfos.setTeam_intro(String.valueOf(getTeamIntro.getContentText()));
                newhiresInfos.setProject_id(hiresInfos.getProject_id());


                UpdateInfosThread updateInfosThread = new UpdateInfosThread(newhiresInfos);

                try {
                    updateInfosThread.start();
                    updateInfosThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

                Toast.makeText(getActivity(), "更新成功！", Toast.LENGTH_SHORT).show();


            }
        });



    }

    public class UpdateInfosThread extends Thread{

        HiresInfos hiresInfos = new HiresInfos();

        public UpdateInfosThread(HiresInfos hiresInfos){
            this.hiresInfos = hiresInfos;
        }


        @Override
        public void run() {
            super.run();

            HiresInfosDao hiresInfosDao = new HiresInfosDao();
            hiresInfosDao.UpdateHiresInfo(hiresInfos);
        }
    }

}
