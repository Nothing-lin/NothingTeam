package com.nothinglin.nothingteam.dao;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.dao.table.HiresInfosTable;
import com.nothinglin.nothingteam.dao.table.HiresInfosTabsTable;
import com.nothinglin.nothingteam.dao.table.TeamLabelsTable;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HiresInfosDao {


    public HiresInfosDao() {
    }

    //获取招聘信息表中的全部内容
    public ArrayList<HiresInfos> getHiresInfoThis(String projectId) {

        String sql = "select * from hires_infos where project_id = "+projectId+"";
        Connection connection = DBOpenHelper.getConnection();
        ArrayList<HiresInfos> hiresInfos = new ArrayList<>();

        //数据库执行体
        /**
         * Jdbc中的statement对象用于向数据库发送SQL语句，想完成对数据库的增删改查，只需要通过这个对象向数据库发送增删改查语句即可。
         * Statement对象的executeUpdate方法，用于向数据库发送增、删、改的sql语句，executeUpdate执行完后，将会返回一个整数（即增删改语句导致了数据库几行数据发生了变化)。
         * Statement.executeQuery方法用于向数据库发送查询语句,executeQuery方法返回代表查询结果的ResultSet对象。
         */
        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            //rs.next() -- 看看数据库有没有下条数据，没有就不遍历了，有的话就继续遍历
            while (rs.next()) {
                HiresInfos hiresInfo = new HiresInfos();

                //将数据库的每条数据放到hireInfo bean中保存，再统一加入list列表中（多条数据）
                hiresInfo.setProject_id(rs.getString(HiresInfosTable.COL_PROJECT_ID));
                hiresInfo.setProject_name(rs.getString(HiresInfosTable.COL_PROJECT_NAME));
                hiresInfo.setProject_owner_team_name(rs.getString(HiresInfosTable.COL_PROJECT_OWNER_TEAM_NAME));
                hiresInfo.setProject_type(rs.getString(HiresInfosTable.COL_PROJECT_TYPE));
                hiresInfo.setCompetition_type(rs.getString(HiresInfosTable.COL_COMPETITION_TYPE));
                hiresInfo.setHire_numbers(rs.getString(HiresInfosTable.COL_HIRE_NUMBERS));
                hiresInfo.setProject_position(rs.getString(HiresInfosTable.COL_PROJECT_POSITION));
                hiresInfo.setProject_create_date(rs.getDate(HiresInfosTable.COL_PROJECT_CREATE_DATE));
                hiresInfo.setIs_team_full(rs.getInt(HiresInfosTable.COL_IS_TEAM_FULL));
                hiresInfo.setIs_hide(rs.getInt(HiresInfosTable.COL_IS_HIDE));
                hiresInfo.setProject_introdution(rs.getString(HiresInfosTable.COL_PROJECT_INTRODUCTION));
                hiresInfo.setTeam_avatar(rs.getString(HiresInfosTable.COL_TEAM_AVATAR));
                hiresInfo.setTeam_school(rs.getString(HiresInfosTable.COL_TEAM_SCHOOL));
                hiresInfo.setTeam_intro(rs.getString(HiresInfosTable.COL_TEAM_INTRO));
                hiresInfo.setTeam_manager_userid(rs.getString(HiresInfosTable.COL_TEAM_MANAGER_USERID));
                hiresInfo.setProject_detail(rs.getString(HiresInfosTable.COL_PROJECT_DETAIL));
                hiresInfo.setHire_detail(rs.getString(HiresInfosTable.COL_HIRE_DETAIL));
                hiresInfo.setGroup_id(rs.getString("group_id"));


                hiresInfos.add(hiresInfo);
            }

            //关闭数据库连接资源
            rs.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hiresInfos;
    }

    //获取招聘信息表中的全部内容
    public List<HiresInfos> getHiresInfoAll() {

        String sql = "select * from hires_infos";
        Connection connection = DBOpenHelper.getConnection();
        List<HiresInfos> hiresInfos = new ArrayList<>();

        //数据库执行体
        /**
         * Jdbc中的statement对象用于向数据库发送SQL语句，想完成对数据库的增删改查，只需要通过这个对象向数据库发送增删改查语句即可。
         * Statement对象的executeUpdate方法，用于向数据库发送增、删、改的sql语句，executeUpdate执行完后，将会返回一个整数（即增删改语句导致了数据库几行数据发生了变化)。
         * Statement.executeQuery方法用于向数据库发送查询语句,executeQuery方法返回代表查询结果的ResultSet对象。
         */
        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            //rs.next() -- 看看数据库有没有下条数据，没有就不遍历了，有的话就继续遍历
            while (rs.next()) {
                HiresInfos hiresInfo = new HiresInfos();

                //将数据库的每条数据放到hireInfo bean中保存，再统一加入list列表中（多条数据）
                hiresInfo.setProject_id(rs.getString(HiresInfosTable.COL_PROJECT_ID));
                hiresInfo.setProject_name(rs.getString(HiresInfosTable.COL_PROJECT_NAME));
                hiresInfo.setProject_owner_team_name(rs.getString(HiresInfosTable.COL_PROJECT_OWNER_TEAM_NAME));
                hiresInfo.setProject_type(rs.getString(HiresInfosTable.COL_PROJECT_TYPE));
                hiresInfo.setCompetition_type(rs.getString(HiresInfosTable.COL_COMPETITION_TYPE));
                hiresInfo.setHire_numbers(rs.getString(HiresInfosTable.COL_HIRE_NUMBERS));
                hiresInfo.setProject_position(rs.getString(HiresInfosTable.COL_PROJECT_POSITION));
                hiresInfo.setProject_create_date(rs.getDate(HiresInfosTable.COL_PROJECT_CREATE_DATE));
                hiresInfo.setIs_team_full(rs.getInt(HiresInfosTable.COL_IS_TEAM_FULL));
                hiresInfo.setIs_hide(rs.getInt(HiresInfosTable.COL_IS_HIDE));
                hiresInfo.setProject_introdution(rs.getString(HiresInfosTable.COL_PROJECT_INTRODUCTION));
                hiresInfo.setTeam_avatar(rs.getString(HiresInfosTable.COL_TEAM_AVATAR));
                hiresInfo.setTeam_school(rs.getString(HiresInfosTable.COL_TEAM_SCHOOL));
                hiresInfo.setTeam_intro(rs.getString(HiresInfosTable.COL_TEAM_INTRO));
                hiresInfo.setTeam_manager_userid(rs.getString(HiresInfosTable.COL_TEAM_MANAGER_USERID));
                hiresInfo.setProject_detail(rs.getString(HiresInfosTable.COL_PROJECT_DETAIL));
                hiresInfo.setHire_detail(rs.getString(HiresInfosTable.COL_HIRE_DETAIL));
                hiresInfo.setGroup_id(rs.getString("group_id"));


                hiresInfos.add(hiresInfo);
            }

            //关闭数据库连接资源
            rs.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hiresInfos;
    }


    //获取招聘信息标签中的全部内容
    public List<HiresInfosTabs> getHiresInfoTabsAll() {
        String sql = "select * from project_requirements";
        Connection connection = DBOpenHelper.getConnection();
        List<HiresInfosTabs> hiresInfosTabs = new ArrayList<>();

        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                HiresInfosTabs hiresInfosTab = new HiresInfosTabs();

                hiresInfosTab.setProject_id(rs.getString(HiresInfosTabsTable.COL_PROJECT_ID));
                hiresInfosTab.setAbility_requirements(rs.getString(HiresInfosTabsTable.COL_ABILITY_REQUIREMENTS));

                hiresInfosTabs.add(hiresInfosTab);

            }

            rs.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return hiresInfosTabs;
    }


    //获取团队标签中的全部内容
    public List<TeamLabel> getTeamLabelsAll() {
        String sql = "select * from team_skill_label";
        Connection connection = DBOpenHelper.getConnection();
        List<TeamLabel> teamLabels = new ArrayList<>();

        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                TeamLabel teamLabel = new TeamLabel();

                teamLabel.setProject_id(rs.getString(TeamLabelsTable.COL_PROJECT_ID));
                teamLabel.setTeam_label(rs.getString(TeamLabelsTable.COL_TEAM_LABEL));

                teamLabels.add(teamLabel);

            }

            rs.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teamLabels;
    }


    //插入招聘信息
    public int InsertHiresInfo(HiresInfos hiresInfos) {
        int project_id = 0;
        String sql = "insert into hires_infos(project_id,project_name,project_owner_team_name,project_type,competition_type,hire_numbers,project_position,project_create_date,is_team_full,is_hide,project_introduction,team_avatar,team_school,team_intro,team_manager_userid,project_detail,hire_detail,group_id)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = (PreparedStatement) connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,hiresInfos.getProject_id());
            pst.setString(2,hiresInfos.getProject_name());
            pst.setString(3,hiresInfos.getProject_owner_team_name());
            pst.setString(4,hiresInfos.getProject_type());
            pst.setString(5,hiresInfos.getCompetition_type());
            pst.setString(6,hiresInfos.getHire_numbers());
            pst.setString(7,hiresInfos.getProject_position());
            pst.setDate(8, new java.sql.Date(hiresInfos.getProject_create_date().getTime()));
            pst.setInt(9,hiresInfos.getIs_team_full());
            pst.setInt(10,hiresInfos.getIs_hide());
            pst.setString(11,hiresInfos.getProject_introdution());
            pst.setString(12,hiresInfos.getTeam_avatar());
            pst.setString(13,hiresInfos.getTeam_school());
            pst.setString(14,hiresInfos.getTeam_intro());
            pst.setString(15,hiresInfos.getTeam_manager_userid());
            pst.setString(16,hiresInfos.getProject_detail());
            pst.setString(17,hiresInfos.getHire_detail());
            pst.setString(18,hiresInfos.getGroup_id());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if(rs.next()){
                project_id = rs.getInt(1);//获取自增的id数值
            }
            pst.close();
            connection.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return project_id;
    }


    //更新招聘信息
    public void UpdateHiresInfo(HiresInfos hiresInfos){
        String sql = "update hires_infos set project_name = ? , project_owner_team_name = ? , project_type = ? , competition_type = ? , hire_numbers = ? , project_position = ? , project_introduction = ? , team_avatar = ? , team_school = ? , team_intro = ? , project_detail = ? , hire_detail = ? where project_id = ?;";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;


        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,hiresInfos.getProject_name());
            pst.setString(2,hiresInfos.getProject_owner_team_name());
            pst.setString(3,hiresInfos.getProject_type());
            pst.setString(4,hiresInfos.getCompetition_type());
            pst.setString(5,hiresInfos.getHire_numbers());
            pst.setString(6,hiresInfos.getProject_position());
            pst.setString(7,hiresInfos.getProject_introdution());
            pst.setString(8,hiresInfos.getTeam_avatar());
            pst.setString(9,hiresInfos.getTeam_school());
            pst.setString(10,hiresInfos.getTeam_intro());
            pst.setString(11,hiresInfos.getProject_detail());
            pst.setString(12,hiresInfos.getHire_detail());
            pst.setString(13,hiresInfos.getProject_id());

            pst.executeUpdate();

            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
