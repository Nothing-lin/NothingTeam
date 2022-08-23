package com.nothinglin.nothingteam.bean;

import java.io.Serializable;

public class TeamLabel implements Serializable {
    private String project_id;
    private String team_label;


    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTeam_label() {
        return team_label;
    }

    public void setTeam_label(String team_label) {
        this.team_label = team_label;
    }

    @Override
    public String toString() {
        return "TeamLabel{" +
                "project_id='" + project_id + '\'' +
                ", team_label='" + team_label + '\'' +
                '}';
    }
}
