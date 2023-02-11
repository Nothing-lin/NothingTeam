package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.CreateActivityFragment;

public class CreateActivitysActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openPage(CreateActivityFragment.class);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
