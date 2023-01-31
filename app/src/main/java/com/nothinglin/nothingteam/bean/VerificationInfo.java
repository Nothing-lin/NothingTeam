package com.nothinglin.nothingteam.bean;

//审批单中未处理的数据，需要群主决定是同意还是拒绝，之后都把数据给删除掉
public class VerificationInfo {
    private String ApplyUserName;//申请者的用户名id
    private String GroupId;//群id
    private String GroupManagerUsername;//群主的用户名id
    private String ApplyText;//申请理由

    private String GroupName;
    private String Avatar;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getApplyText() {
        return ApplyText;
    }

    public void setApplyText(String applyText) {
        ApplyText = applyText;
    }

    public String getApplyUserName() {
        return ApplyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        ApplyUserName = applyUserName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupManagerUsername() {
        return GroupManagerUsername;
    }

    public void setGroupManagerUsername(String groupManagerUsername) {
        GroupManagerUsername = groupManagerUsername;
    }
}
