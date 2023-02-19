package com.nothinglin.nothingteam.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.dao.table.CommentDetailTable;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityDetailCommentDao {

    public List<CommentDetail> getAllMyComment(String managerid){
        String sql = "select * from activity_comment where managerid ="+managerid+"";
        Connection connection = DBOpenHelper.getConnection();
        List<CommentDetail> commentDetails = new ArrayList<>();

        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CommentDetail commentDetail = new CommentDetail();

                commentDetail.setProject_id(rs.getString("activity_id"));
                commentDetail.setUser_name(rs.getString("user_name"));
                commentDetail.setComment_time(rs.getTime("comment_time"));
                commentDetail.setComment_content(rs.getString("comment_content"));
                commentDetail.setManagerid(rs.getString("managerid"));

                commentDetails.add(commentDetail);
            }

            rs.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return commentDetails;
    }


    public List<CommentDetail> getAllComment(){
        String sql = "select * from activity_comment";
        Connection connection = DBOpenHelper.getConnection();
        List<CommentDetail> commentDetails = new ArrayList<>();

        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CommentDetail commentDetail = new CommentDetail();

                commentDetail.setProject_id(rs.getString("activity_id"));
                commentDetail.setUser_name(rs.getString("user_name"));
                commentDetail.setComment_time(rs.getTime("comment_time"));
                commentDetail.setComment_content(rs.getString("comment_content"));
                commentDetail.setManagerid(rs.getString("managerid"));

                commentDetails.add(commentDetail);
            }

            rs.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return commentDetails;
    }


    //插入评论到数据库中
    public void InsetComment(String project_id, String username, Date time,String content,String managerid){
        String sql = "insert into activity_comment(activity_id,user_name,comment_time,comment_content,managerid) value(?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        Date currentDate = Calendar.getInstance().getTime();

        try {
            pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1,project_id);
            pst.setString(2,username);
            pst.setTimestamp(3,new Timestamp(currentDate.getTime()));
            pst.setString(4,content);
            pst.setString(5,managerid);

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //删除对应的评论
    public void DeleteComment(String project_id, String username,String content){
        String sql = "delete from activity_comment where activity_id='"+project_id+"'and user_name='"+username+"'and comment_content='"+content+"'";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;
        try {
            pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
