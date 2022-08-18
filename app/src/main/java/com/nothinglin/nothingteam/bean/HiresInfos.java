package com.nothinglin.nothingteam.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HiresInfos {
    private int id;
    private String project_id;
    private String project_name;
    private String project_owner_team_name;
    private String project_type;
    private String competition_type;
    private String hire_numbers;
    private String project_position;
    private Date project_create_date;
    private int is_team_full;
    private int is_hide;
    private String project_introdution;
    //装载标签的list
    private List<HiresInfosTabs> tabs;

    public List<HiresInfosTabs> getTabs() {
        return tabs;
    }

    public void setTabs(List<HiresInfosTabs> tabs) {
        this.tabs = tabs;
    }

    public String getHire_numbers() {
        return hire_numbers;
    }

    public void setHire_numbers(String hire_numbers) {
        this.hire_numbers = hire_numbers;
    }

    public String getProject_introdution() {
        return project_introdution;
    }

    public void setProject_introdution(String project_introdution) {
        this.project_introdution = project_introdution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_owner_team_name() {
        return project_owner_team_name;
    }

    public void setProject_owner_team_name(String project_owner_team_name) {
        this.project_owner_team_name = project_owner_team_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getCompetition_type() {
        return competition_type;
    }

    public void setCompetition_type(String competition_type) {
        this.competition_type = competition_type;
    }

    public String getProject_position() {
        return project_position;
    }

    public void setProject_position(String project_position) {
        this.project_position = project_position;
    }

    public Date getProject_create_date() {
        return project_create_date;
    }

    public void setProject_create_date(Date project_create_date) {
        this.project_create_date = project_create_date;
    }

    public int getIs_team_full() {
        return is_team_full;
    }

    public void setIs_team_full(int is_team_full) {
        this.is_team_full = is_team_full;
    }

    public int getIs_hide() {
        return is_hide;
    }

    public void setIs_hide(int is_hide) {
        this.is_hide = is_hide;
    }


    @Override
    public String toString() {
        return "HiresInfos{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", project_owner_team_name='" + project_owner_team_name + '\'' +
                ", project_type='" + project_type + '\'' +
                ", competition_type='" + competition_type + '\'' +
                ", hire_numbers=" + hire_numbers +
                ", project_position='" + project_position + '\'' +
                ", project_create_date=" + project_create_date +
                ", is_team_full=" + is_team_full +
                ", is_hide=" + is_hide +
                '}';
    }
}
