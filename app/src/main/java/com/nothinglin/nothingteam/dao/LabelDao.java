package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabelDao {

    public void InsertProjectLabel(String projectId,String projectLabel){

        String sql = "insert into project_requirements(project_id,ability_requirements)value(?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,projectId);
            pst.setString(2,projectLabel);

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void InsertTeamLabel(String projectId,String teamLabel){

        String sql = "insert into team_skill_label(project_id,team_label)value(?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,projectId);
            pst.setString(2,teamLabel);

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
