package com.nothinglin.nothingteam.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityDetailEditFragment;
import com.nothinglin.nothingteam.fragment.activitypages.ActivityDetailFragment;

import java.util.ArrayList;


public class ActivityDetailEditActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<ActivityInfo> activityInfoList = new ArrayList<>();
        Intent intent = getIntent();
        activityInfoList = (ArrayList<ActivityInfo>) intent.getSerializableExtra("activityInfo");

        Bundle bundle = new Bundle();
        bundle.putSerializable("activityInfo",activityInfoList);


        openPage(ActivityDetailEditFragment.class,bundle);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

}