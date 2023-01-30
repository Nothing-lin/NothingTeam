package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VerificationInfoDao {


    //插入数据
    public void InsertVerificationInfo(VerificationInfo verificationInfo){
        String sql = "insert into verification_list(apply_username,group_id,group_manager_username,apply_text)value(?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;


        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,verificationInfo.getApplyUserName());
            pst.setString(2,verificationInfo.getGroupId());
            pst.setString(3,verificationInfo.getGroupManagerUsername());
            pst.setString(4,verificationInfo.getApplyText());

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //根据群主的身份获取看看有哪些未审批的数据
    public List<VerificationInfo> getAboutManagerData(String managerUsername){
        String sql = "select * from verification_list where group_manager_username = "+managerUsername;
        Connection connection = DBOpenHelper.getConnection();
        List<VerificationInfo> verificationInfos = new ArrayList<>();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                VerificationInfo verificationInfo = new VerificationInfo();

                verificationInfo.setApplyUserName(rs.getString("apply_username"));
                verificationInfo.setGroupId(rs.getString("group_id"));
                verificationInfo.setGroupManagerUsername("group_manager_username");
                verificationInfo.setApplyText("apply_text");

                verificationInfos.add(verificationInfo);


            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationInfos;
    }


    //获取申请者的数据，看申请者是否提交过申请给这个群了，提交过就提示请勿重复申请
    //根据群主的身份获取看看有哪些未审批的数据
    public Boolean IsApplyManApplyThisGroup(String managerUsername,String groupId){
        String sql = "select * from verification_list where group_manager_username = '"+managerUsername+"' and group_id = '"+groupId+"'";
        Connection connection = DBOpenHelper.getConnection();
        List<VerificationInfo> verificationInfos = new ArrayList<>();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                VerificationInfo verificationInfo = new VerificationInfo();

                verificationInfo.setApplyUserName(rs.getString("apply_username"));
                verificationInfo.setGroupId(rs.getString("group_id"));
                verificationInfo.setGroupManagerUsername("group_manager_username");
                verificationInfo.setApplyText("apply_text");

                verificationInfos.add(verificationInfo);


            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (verificationInfos.isEmpty()){
            //申请者之前没有申请过
            return false;
        }else {
            //有数据就说明申请过了
            return true;
        }
    }
}
