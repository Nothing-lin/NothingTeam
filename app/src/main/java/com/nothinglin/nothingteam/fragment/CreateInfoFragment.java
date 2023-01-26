package com.nothinglin.nothingteam.fragment;

import static com.xuexiang.xutil.XUtil.getContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mysql.jdbc.Connection;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "创建项目")
public class CreateInfoFragment extends BaseFragment {

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

    public Uri uri;

    public HiresInfos hiresInfos = new HiresInfos();



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_createinfo;
    }

    @Override
    protected void initViews() {
        //头像选择
        getTeamAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,0);
            }
        });




        //单击触发
        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiresInfos.setProject_name(String.valueOf(getProjectName.getText()));
                hiresInfos.setProject_owner_team_name(String.valueOf(getTeamName.getText()));
                hiresInfos.setProject_type(String.valueOf(getProjectType.getText()));
                hiresInfos.setCompetition_type(String.valueOf(getCompetitionType.getText()));
                hiresInfos.setHire_numbers(String.valueOf(getHireNumbers.getText()));
                hiresInfos.setTeam_school(String.valueOf(getTeamSchool.getText()));
                hiresInfos.setProject_position(String.valueOf(getProjectposition.getText()));
                hiresInfos.setProject_introdution(String.valueOf(getProjectIntroduction.getContentText()));
                hiresInfos.setProject_detail(String.valueOf(getProjectDetail.getContentText()));
                hiresInfos.setTeam_intro(String.valueOf(getTeamIntro.getContentText()));
                hiresInfos.setHire_detail(String.valueOf(getHireDetail.getContentText()));

                //设置创建时间
                Date currentTime = new Date();
                hiresInfos.setProject_create_date(currentTime);


                Thread insertHireInfos = new InsertHireinfosThread(hiresInfos);

                try {
                    insertHireInfos.start();
                    insertHireInfos.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);


            }
        });

    }

    public class  InsertHireinfosThread extends Thread{

        HiresInfos hiresInfos = new HiresInfos();

        InsertHireinfosThread(HiresInfos hiresInfos){
            this.hiresInfos = hiresInfos;
        }


        @Override
        public void run() {
            super.run();
            //需要这些looper才能让弹窗出来
//            Looper.prepare();

            //获取创建该招聘信息管理者的id
            UserInfo userInfo = JMessageClient.getMyInfo();
            hiresInfos.setTeam_manager_userid(String.valueOf(userInfo.getUserID()));

            new HiresInfosDao().InsertHiresInfo(hiresInfos);
//            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
//            Looper.loop();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == -1){

            uri = data.getData();
            getTeamAvatar.setImageURI(uri);
            //将头像图片转换成base64编码

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                //将图片转换成二进制写入数据库中
                hiresInfos.setTeam_avatar(Base64.encodeToString(imageBytes,Base64.DEFAULT));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
}
