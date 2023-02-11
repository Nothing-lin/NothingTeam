package com.nothinglin.nothingteam.fragment.activitypages;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ActivityDoingListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActivityWillFragment extends BaseFragment {

    @BindView(R.id.activity_doing_list_view)
    ListView mListView;

    private Activity mContext;
    private ActivityDoingListAdapter mAdapter;

    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    public ActivityWillFragment(List<ActivityInfo> activityInfoList) {
        this.activityInfoList = activityInfoList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {

        mContext = getActivity();
        mAdapter = new ActivityDoingListAdapter(mContext,activityInfoList);
        mListView.setAdapter(mAdapter);

    }
}
