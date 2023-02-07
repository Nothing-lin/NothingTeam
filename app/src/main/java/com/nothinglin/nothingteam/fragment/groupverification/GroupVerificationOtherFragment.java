package com.nothinglin.nothingteam.fragment.groupverification;

import android.app.Activity;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.GroupVerificationAdapter;
import com.nothinglin.nothingteam.adapter.GroupVerificationMessageAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.bean.VerificationReply;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.List;

import butterknife.BindView;

public class GroupVerificationOtherFragment extends BaseFragment {

    private Activity mContext;

    @BindView(R.id.group_recommend_list_view)
    ListView mListView;

    private List<VerificationReply> mRecommends;
    private GroupVerificationMessageAdapter mAdapter;

    public GroupVerificationOtherFragment(List<VerificationReply> mRecommends){
        this.mRecommends = mRecommends;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_verification_other;
    }

    @Override
    protected void initViews() {
        mContext = getActivity();
        mAdapter = new GroupVerificationMessageAdapter(mContext, mRecommends);
        mListView.setAdapter(mAdapter);
    }
}
