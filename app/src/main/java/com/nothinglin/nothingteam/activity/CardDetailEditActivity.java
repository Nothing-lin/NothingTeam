package com.nothinglin.nothingteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.fragment.CardDetailEditFragment;
import com.nothinglin.nothingteam.fragment.CreateActivityFragment;

import java.util.ArrayList;

public class CardDetailEditActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        ArrayList<HiresInfos> detailCardInfo = new ArrayList<>();
        detailCardInfo = (ArrayList<HiresInfos>) intent.getSerializableExtra("detailCardInfo");

        Bundle bundle = new Bundle();
        bundle.putSerializable("detailCardInfo",detailCardInfo);
        openPage(CardDetailEditFragment.class,bundle);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
