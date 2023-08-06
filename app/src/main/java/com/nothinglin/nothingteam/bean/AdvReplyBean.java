package com.nothinglin.nothingteam.bean;

import java.io.Serializable;

public class AdvReplyBean implements Serializable {
    private String userid;
    private String reply;
    private String project_name;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
