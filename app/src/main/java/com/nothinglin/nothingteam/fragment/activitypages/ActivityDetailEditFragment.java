package com.nothinglin.nothingteam.fragment.activitypages;

import static com.xuexiang.xutil.XUtil.getContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.dao.ActivityInfoDao;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

@Page(name = "活动详情页编辑")
public class ActivityDetailEditFragment extends BaseFragment {
    @BindView(R.id.activity_name)
    ClearEditText ActivityName;
    @BindView(R.id.activity_position)
    ClearEditText ActivityPosition;
    @BindView(R.id.activity_type)
    ClearEditText ActivityType;
    @BindView(R.id.activity_user)
    ClearEditText ActivityUser;
    @BindView(R.id.activity_start_time)
    TextView ActivityStartTime;
    @BindView(R.id.bt_select_start)
    Button btSelectStart;
    @BindView(R.id.activity_end_time)
    TextView ActivityEndTime;
    @BindView(R.id.bt_select_end)
    Button btSelectEnd;
    @BindView(R.id.activity_picture)
    ImageView ActivityPicture;
    @BindView(R.id.activity_detail)
    MultiLineEditText ActivityDetail;

    @BindView(R.id.create_activity)
    Button CreateActivity;

    private TimePickerView mStartTimePickerDialog;
    private TimePickerView mEndTimePickerDialog;

    private ArrayList<ActivityInfo> activityInfoList = new ArrayList<>();
    private ActivityInfo activityInfo = new ActivityInfo();
    public Uri uri;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_activity;
    }

    @Override
    protected void initViews() {

        //获取传过来的详情页数据
        Bundle bundle = getArguments();
        activityInfoList = (ArrayList<ActivityInfo>) bundle.getSerializable("activityInfo");
        activityInfo = activityInfoList.get(0);

        ActivityName.setText(activityInfo.getActivityName());
        ActivityPosition.setText(activityInfo.getActivityPosition());
        ActivityType.setText(activityInfo.getActivityType());
        ActivityUser.setText(activityInfo.getActivityUser());
        ActivityDetail.setContentText(activityInfo.getActivityDetail());

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = format1.parse(activityInfo.getActivityStartTime());
            SimpleDateFormat outputFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            ActivityStartTime.setText(outputFormat1.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date2 = format2.parse(activityInfo.getActivityEndTime());
            SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            ActivityStartTime.setText(outputFormat2.format(date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (activityInfo.getActivityAvatar() != null) {
            //图片设置
            //对缩略图进行base64转码
            byte[] imageBytes = Base64.decode(activityInfo.getActivityAvatar(), Base64.DEFAULT);
            Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ActivityPicture.setImageBitmap(decodeImage);
        }


        //时间选择器
        btSelectStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //XUI时间选择器
                if (mStartTimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.string2Date("2023-01-01 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
                    mStartTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            ActivityStartTime.setText(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                            activityInfo.setActivityStartTime(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                        }
                    }).setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setType(TimePickerType.ALL)
                            .setTitleText("时间选择")
                            .isDialog(true)
                            .setOutSideCancelable(false)
                            .setDate(calendar)
                            .build();
                }
                mStartTimePickerDialog.show();

            }
        });

        //时间选择器
        btSelectEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //XUI时间选择器
                if (mEndTimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.string2Date("2023-01-01 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
                    mEndTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            ActivityEndTime.setText(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                            activityInfo.setActivityEndTime(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                        }
                    }).setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setType(TimePickerType.ALL)
                            .setTitleText("时间选择")
                            .isDialog(true)
                            .setOutSideCancelable(false)
                            .setDate(calendar)
                            .build();
                }
                mEndTimePickerDialog.show();
            }
        });


        ActivityPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,1);
            }
        });


        CreateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityInfo.setActivityName(String.valueOf(ActivityName.getText()));
                activityInfo.setActivityPosition(String.valueOf(ActivityPosition.getText()));
                activityInfo.setActivityType(String.valueOf(ActivityType.getText()));
                activityInfo.setActivityUser(String.valueOf(ActivityUser.getText()));
                activityInfo.setActivityDetail(ActivityDetail.getContentText());

                UpdateActivityThread updateActivityThread = new UpdateActivityThread(activityInfo);

                try {
                    updateActivityThread.start();
                    updateActivityThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "更新成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();



            }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1){

            uri = data.getData();
            ActivityPicture.setImageURI(uri);
            //将头像图片转换成base64编码
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageBytes = baos.toByteArray();
                //将图片转换成二进制写入数据库中
                String temp = Base64.encodeToString(imageBytes,Base64.DEFAULT);
                this.activityInfo.setActivityAvatar(temp);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public class UpdateActivityThread extends Thread{

        private ActivityInfo activityInfo = new ActivityInfo();

        public UpdateActivityThread(ActivityInfo activityInfo){
            this.activityInfo = activityInfo;
        }

        @Override
        public void run() {
            super.run();
            ActivityInfoDao activityInfoDao = new ActivityInfoDao();
            activityInfoDao.UpdateActivityInfo(activityInfo);
        }
    }
}
