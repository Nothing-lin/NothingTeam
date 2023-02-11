package com.nothinglin.nothingteam.bean;

import java.util.Date;

public class ActivityInfo {

    private String ActivityId;
    private String ActivityPosition;
    private String ActivityName;
    private String ActivityAvatar;
    private String ActivityStartTime;
    private String ActivityEndTime;
    private String ActivityUser;
    private String ActivityDetail;
    private String ActivityType;
    private String ActivityManagerId;

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getActivityPosition() {
        return ActivityPosition;
    }

    public void setActivityPosition(String activityPosition) {
        ActivityPosition = activityPosition;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityAvatar() {
        return ActivityAvatar;
    }

    public void setActivityAvatar(String activityAvatar) {
        ActivityAvatar = activityAvatar;
    }

    public String getActivityStartTime() {
        return ActivityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        ActivityStartTime = activityStartTime;
    }

    public String getActivityEndTime() {
        return ActivityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        ActivityEndTime = activityEndTime;
    }

    public String getActivityUser() {
        return ActivityUser;
    }

    public void setActivityUser(String activityUser) {
        ActivityUser = activityUser;
    }

    public String getActivityDetail() {
        return ActivityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        ActivityDetail = activityDetail;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }

    public String getActivityManagerId() {
        return ActivityManagerId;
    }

    public void setActivityManagerId(String activityManagerId) {
        ActivityManagerId = activityManagerId;
    }

    @Override
    public String toString() {
        return "ActivityInfo{" +
                "ActivityId='" + ActivityId + '\'' +
                ", ActivityPosition='" + ActivityPosition + '\'' +
                ", ActivityName='" + ActivityName + '\'' +
                ", ActivityAvatar='" + ActivityAvatar + '\'' +
                ", ActivityStartTime='" + ActivityStartTime + '\'' +
                ", ActivityEndTime='" + ActivityEndTime + '\'' +
                ", ActivityUser='" + ActivityUser + '\'' +
                ", ActivityDetail='" + ActivityDetail + '\'' +
                ", ActivityType='" + ActivityType + '\'' +
                ", ActivityManagerId='" + ActivityManagerId + '\'' +
                '}';
    }
}
