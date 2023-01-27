package com.nothinglin.nothingteam.fragment;

import static com.xuexiang.xutil.XUtil.getContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.DetailPicture;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.dao.LabelDao;
import com.nothinglin.nothingteam.dao.PictureDao;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        //缩略图123的选择
        getDetailPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,1);
            }
        });
        getDetailPicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
        getDetailPicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,3);
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


                Thread insertHireInfos = new InsertHireinfosThread();

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

        @Override
        public void run() {
            super.run();
            //需要这些looper才能让弹窗出来
//            Looper.prepare();

            //获取创建该招聘信息管理者的id
            UserInfo userInfo = JMessageClient.getMyInfo();
            hiresInfos.setTeam_manager_userid(String.valueOf(userInfo.getUserID()));

            //获取项目的自增project_id-----------------------------------------------------------------
            hiresInfos.setProject_id(String.valueOf(new HiresInfosDao().InsertHiresInfo(hiresInfos)));
//            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
//            Looper.loop();

            //插入数据库转码后的图片，详情页的图片插入mysql
            if (detailPicture1 != null){
                PictureDao pictureDao = new PictureDao();
                detailPicture1.setProject_id(hiresInfos.getProject_id());
                pictureDao.InsetDetailPicture(detailPicture1);
            }

            if (detailPicture2 != null){
                detailPicture2.setProject_id(hiresInfos.getProject_id());
                new PictureDao().InsetDetailPicture(detailPicture2);
            }

            if (detailPicture3 != null){
                detailPicture3.setProject_id(hiresInfos.getProject_id());
                new PictureDao().InsetDetailPicture(detailPicture3);
            }
            //--------------------------------------------------------------------------------------

            //项目的需求标签---------------------------------------------------------------------------
            String projectLabel = String.valueOf(getProjectRequirement.getText());//获取输入的字符串
            //将字符串按照|进行分隔，获取标签数组
            String[] labelArray = projectLabel.split("[|]");
            //System.out.println(labelArray);

            //将分隔出来的标签写入数据库中
            for (int i = 0;i < labelArray.length;i++){
                LabelDao labelDao = new LabelDao();
                labelDao.InsertProjectLabel(hiresInfos.getProject_id(),labelArray[i]);

            }

            //--------------------------------------------------------------------------------------


            //团队标签--------------------------------------------------------------------------------
            String teamLabel = String.valueOf(getTeamLabel.getText());
            //将字符串按照|进行分隔，获取标签数组
            String[] teamlabelArray = teamLabel.split("[|]");


            //将分隔出来的标签写入数据库中
            for (int i = 0;i<teamlabelArray.length;i++){
                LabelDao TeamlabelDao = new LabelDao();
                TeamlabelDao.InsertTeamLabel(hiresInfos.getProject_id(),teamlabelArray[i]);
            }


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

        if (requestCode ==1 && resultCode == -1){
            uri = data.getData();
            getDetailPicture1.setImageURI(uri);

            //图片 --> base64转码
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                //将图片转换成二进制写入数据库中
//                detailPicture1.setProject_id(hiresInfos.getProject_id());
                detailPicture1.setDetail_picture(Base64.encodeToString(imageBytes,Base64.DEFAULT));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (requestCode ==2 && resultCode == -1){
            uri = data.getData();
            getDetailPicture2.setImageURI(uri);

            //图片 --> base64转码
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                //将图片转换成二进制写入数据库中
//                detailPicture2.setProject_id(hiresInfos.getProject_id());
                detailPicture2.setDetail_picture(Base64.encodeToString(imageBytes,Base64.DEFAULT));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode ==3 && resultCode == -1){
            uri = data.getData();
            getDetailPicture3.setImageURI(uri);

            //图片 --> base64转码
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                //将图片转换成二进制写入数据库中
//                detailPicture3.setProject_id(hiresInfos.getProject_id());
                detailPicture3.setDetail_picture(Base64.encodeToString(imageBytes,Base64.DEFAULT));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
