package com.nothinglin.nothingteam.bean;

import java.io.Serializable;

public class HiresInfosTabs implements Serializable {
    private int id;
    private String project_id;
    private String ability_requirements;

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

    public String getAbility_requirements() {
        return ability_requirements;
    }

    public void setAbility_requirements(String ability_requirements) {
        this.ability_requirements = ability_requirements;
    }

    @Override
    public String toString() {
        return "HiresInfosTabs{" +
                "id=" + id +
                ", project_id='" + project_id + '\'' +
                ", ability_requirements='" + ability_requirements + '\'' +
                '}';
    }
}
