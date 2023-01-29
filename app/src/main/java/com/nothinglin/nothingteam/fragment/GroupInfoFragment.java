package com.nothinglin.nothingteam.fragment;

import static com.xuexiang.xutil.system.ClipboardUtils.getIntent;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.GroupUserAdapter;
import com.nothinglin.nothingteam.adapter.SingleMessageListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.utils.GlobalThreadPool;
import com.xuexiang.xpage.annotation.Page;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetGroupMembersCallback;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "群组资料")
public class GroupInfoFragment extends BaseFragment {

    @BindView(R.id.lv_group_members)
    ListView list_group_member;

    private GroupUserAdapter memberListAdapter;
    private List<GroupMemberInfo> groupMemberData;
    private long groupID;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_info;
    }

    @Override
    protected void initViews() {
        initData();


    }

    private void initData() {
        Bundle bundle = getArguments();
        groupID = bundle.getLong("groupId");
//        System.out.println(groupID);

        //获取群成员信息List
        JMessageClient.getGroupMembers(groupID, new GetGroupMembersCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {

                memberListAdapter = new GroupUserAdapter(getContext(),list);
                list_group_member.setAdapter(memberListAdapter);
            }
        });






    }
}
