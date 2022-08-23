package com.nothinglin.nothingteam.activity;


import android.content.Intent;
import android.os.Bundle;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.fragment.CardDetailFragment;

import java.util.ArrayList;


public class CardDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从ToolTabCardListFragment获取被选中的卡片信息，在从这里传递给CardDetailFragment
        //因为intent只能够在activity中被获取，不能在fragment中，所以需要一些中间媒介来传递
        Intent intent = getIntent();
        ArrayList<HiresInfos> detailCardInfo = new ArrayList<>();
        detailCardInfo = (ArrayList<HiresInfos>) intent.getSerializableExtra("detailCardInfo");

        Bundle bundle = new Bundle();
        bundle.putSerializable("detailCardInfo",detailCardInfo);

        openPage(CardDetailFragment.class,bundle);


    }

}