package com.nothinglin.nothingteam.activity;


import android.os.Bundle;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.CardDetailFragment;


public class CardDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openPage(CardDetailFragment.class);


    }

}