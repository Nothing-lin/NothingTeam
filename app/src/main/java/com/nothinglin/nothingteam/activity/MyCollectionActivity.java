package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.MyCollectionFragment;

public class MyCollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openPage(MyCollectionFragment.class);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
