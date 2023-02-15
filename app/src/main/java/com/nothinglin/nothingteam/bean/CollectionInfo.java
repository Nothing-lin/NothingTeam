package com.nothinglin.nothingteam.bean;

import java.util.Date;

public class CollectionInfo {
    private String ActivityId;
    private String ActivityName;
    private String AcountId;
    private String ActivityManagerId;
    private Date ActivityStartTime;
    private Date ActivityEndTime;


    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getAcountId() {
        return AcountId;
    }

    public void setAcountId(String acountId) {
        AcountId = acountId;
    }

    public String getActivityManagerId() {
        return ActivityManagerId;
    }

    public void setActivityManagerId(String activityManagerId) {
        ActivityManagerId = activityManagerId;
    }

    public Date getActivityStartTime() {
        return ActivityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        ActivityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return ActivityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        ActivityEndTime = activityEndTime;
    }
}
