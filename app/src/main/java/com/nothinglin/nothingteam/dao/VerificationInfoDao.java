package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.bean.VerificationReply;
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
        String sql = "insert into verification_list(apply_username,group_id,group_manager_username,apply_text,group_name,avatar)value(?,?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;


        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,verificationInfo.getApplyUserName());
            pst.setString(2,verificationInfo.getGroupId());
            pst.setString(3,verificationInfo.getGroupManagerUsername());
            pst.setString(4,verificationInfo.getApplyText());
            pst.setString(5,verificationInfo.getGroupName());
            pst.setString(6,verificationInfo.getAvatar());

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
                verificationInfo.setGroupManagerUsername(rs.getString("group_manager_username"));
                verificationInfo.setApplyText(rs.getString("apply_text"));
                verificationInfo.setGroupName(rs.getString("group_name"));
                verificationInfo.setAvatar(rs.getString("avatar"));

                verificationInfos.add(verificationInfo);


            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationInfos;
    }

    //获取我申请的群申请信息
    public List<VerificationReply> getAboutMyApplication(String myUsername){
        String sql = "select * from verification_reply where apply_username = "+myUsername;
        Connection connection = DBOpenHelper.getConnection();
        List<VerificationReply> verificationReplies = new ArrayList<>();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                VerificationReply verificationReply = new VerificationReply();

                verificationReply.setGroundId(rs.getString("groud_id"));
                verificationReply.setApplyUsername(rs.getString("apply_username"));
                verificationReply.setProjectName(rs.getString("project_name"));
                verificationReply.setTips(rs.getString("tips"));
                verificationReply.setAvatar(rs.getString("avatar"));

                verificationReplies.add(verificationReply);


            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationReplies;
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

    public void DeleteThieApply(String username,String GroudID){
        //Mysql语句
        String sql = "delete from verification_list where apply_username = ? and group_id = ?";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try{
            pst = connection.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,GroudID);

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //修改群验证消息 -- 同意
    public void updateVerificationReplyYes(String username,String groudId){
        String sql = "update verification_reply set tips = ? where apply_username=? and groud_id=?";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,"您已通过该群的群申请~");
            pst.setString(2,username);
            pst.setString(3,groudId);

            pst.executeUpdate();
            pst.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //修改群验证消息 -- 拒绝
    public void updateVerificationReplyNo(String username,String groudId){
        String sql = "update verification_reply set tips = ? where apply_username=? and groud_id=?";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,"抱歉，您没有通过该群的群申请~");
            pst.setString(2,username);
            pst.setString(3,groudId);

            pst.executeUpdate();
            pst.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //插入群验证消息
    public void InsertVerificationReply(VerificationReply verificationReply){
        String sql = "insert into verification_reply(groud_id,apply_username,project_name,tips,avatar)value(?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,verificationReply.getGroundId());
            pst.setString(2,verificationReply.getApplyUsername());
            pst.setString(3,verificationReply.getProjectName());
            pst.setString(4,verificationReply.getTips());
            pst.setString(5,verificationReply.getAvatar());

            pst.executeUpdate();
            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
