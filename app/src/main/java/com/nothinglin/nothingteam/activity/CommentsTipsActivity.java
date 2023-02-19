package com.nothinglin.nothingteam.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.CommentFragment;
import com.nothinglin.nothingteam.fragment.CreateInfoFragment;

public class CommentsTipsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage(CommentFragment.class);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
