package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.ActivityCollectionFragment;
import com.nothinglin.nothingteam.fragment.MyCollectionFragment;

public class ActivityCollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openPage(ActivityCollectionFragment.class);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
