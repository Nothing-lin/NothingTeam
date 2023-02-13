package com.nothinglin.nothingteam.fragment.activitypages;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ActivityListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActivityDoneFragment extends BaseFragment {

    @BindView(R.id.activity_doing_list_view)
    ListView mListView;

    private Activity mContext;
    private ActivityListAdapter mAdapter;

    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    public ActivityDoneFragment(List<ActivityInfo> activityInfoList) {
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
        mAdapter = new ActivityListAdapter(mContext,activityInfoList);
        mListView.setAdapter(mAdapter);

    }
}
