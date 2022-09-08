package com.nothinglin.nothingteam.fragment.teampages;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.GroupListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;

public class MyTeamFragment extends BaseFragment {


    private GroupListAdapter mGroupListAdapter;
    private Context mContext;

    @BindView(R.id.group_list)
    ListView mGroupList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team_group_list;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {

        mContext = getContext();

        final List<GroupInfo> infoList = new ArrayList<>();
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, final List<Long> groupIDList) {
                if (responseCode == 0) {
                    final int[] groupSize = {groupIDList.size()};
                    if (groupIDList.size() > 0) {
                        for (Long id : groupIDList) {
                            JMessageClient.getGroupInfo(id, new GetGroupInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, GroupInfo groupInfo) {
                                    if (i == 0) {
                                        groupSize[0] = groupSize[0] - 1;
                                        infoList.add(groupInfo);
                                        if (groupSize[0] == 0) {
                                            mGroupListAdapter = new GroupListAdapter(mContext, infoList);
                                            mGroupList.setAdapter(mGroupListAdapter);
                                        }

                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getContext(), "您还没有群组", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "出错："+responseMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
