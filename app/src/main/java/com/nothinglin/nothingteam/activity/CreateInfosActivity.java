package com.nothinglin.nothingteam.activity;

import android.os.Bundle;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.CreateInfoFragment;

public class CreateInfosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openPage(CreateInfoFragment.class);
    }
}
