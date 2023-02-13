package com.nothinglin.nothingteam.fragment.activitypages;

import static com.xuexiang.xutil.system.ClipboardUtils.getIntent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.xuexiang.xpage.annotation.Page;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

@Page(name = "活动详情页")
public class ActivityDetailFragment extends BaseFragment {

    @BindView(R.id.detail_picture)
    ImageView mDtailPicture;
    @BindView(R.id.detail_title)
    TextView mDetailTitle;
    @BindView(R.id.detail_content)
    TextView mDetailContent;
    @BindView(R.id.detail_positon)
    TextView mDetailPosition;
    @BindView(R.id.detail_startTime)
    TextView mStartTime;
    @BindView(R.id.detail_endTime)
    TextView mEndTime;
    @BindView(R.id.detail_union)
    TextView mDetailUnion;

    private ArrayList<ActivityInfo> activityInfoList = new ArrayList<>();
    private ActivityInfo activityInfo = new ActivityInfo();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_detail;
    }

    @Override
    protected void initViews() {

        //获取传过来的详情页数据
        Bundle bundle = getArguments();
        activityInfoList = (ArrayList<ActivityInfo>) bundle.getSerializable("activityInfo");
        activityInfo = activityInfoList.get(0);


        if (activityInfo.getActivityAvatar() != null) {
            //图片设置
            //对缩略图进行base64转码
            byte[] imageBytes = Base64.decode(activityInfo.getActivityAvatar(), Base64.DEFAULT);
            Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            mDtailPicture.setImageBitmap(decodeImage);
        }
            mDetailTitle.setText(activityInfo.getActivityName());
            mDetailContent.setText(activityInfo.getActivityDetail());
            mDetailPosition.setText(activityInfo.getActivityPosition());

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = format1.parse(activityInfo.getActivityStartTime());
            SimpleDateFormat outputFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            mStartTime.setText(outputFormat1.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date2 = format2.parse(activityInfo.getActivityEndTime());
            SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            mEndTime.setText(outputFormat2.format(date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
}
