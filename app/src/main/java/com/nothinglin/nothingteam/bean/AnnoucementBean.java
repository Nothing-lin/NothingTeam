package com.nothinglin.nothingteam.bean;

import java.io.Serializable;

public class AnnoucementBean implements Serializable {
    private String title;
    private String content;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
