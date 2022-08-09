package com.nothinglin.nothingteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //注册XUI主题
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //测试简单的对话框
        findViewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new MaterialDialog.Builder(this)//getContext()==this
                .iconRes(R.drawable.icon_tip)
                .title(R.string.tip_infos)
                .content(R.string.content_simple_confirm_dialog)
                .positiveText(R.string.lab_submit)
                .show();
    }
}