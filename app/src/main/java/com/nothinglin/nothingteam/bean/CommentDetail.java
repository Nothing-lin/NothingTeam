package com.nothinglin.nothingteam.bean;

import java.util.Date;

public class CommentDetail {
    private String project_id;
    private String user_name;
    private Date comment_time;
    private String comment_content;
    private String managerid;


    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getComment_time() {
        return comment_time;
    }

    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    @Override
    public String toString() {
        return "CommentDetail{" +
                "project_id='" + project_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", comment_time=" + comment_time +
                ", comment_content='" + comment_content + '\'' +
                '}';
    }
}
