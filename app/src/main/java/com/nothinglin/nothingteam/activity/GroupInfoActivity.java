package com.nothinglin.nothingteam.activity;

import static com.xuexiang.xutil.system.ClipboardUtils.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.GroupInfoFragment;

public class GroupInfoActivity extends BaseActivity {
    private Long groupID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        groupID = intent.getLongExtra("groupId",0);

        Bundle bundle = new Bundle();
        bundle.putLong("groupId",groupID);

        openPage(GroupInfoFragment.class,bundle);

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
