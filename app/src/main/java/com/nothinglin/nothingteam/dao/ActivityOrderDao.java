package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityOrderDao {

    public List<CollectionInfo> getAllMyCollection(String userid){
        String sql = "select * from activity_collection where acount_id = "+userid+"";
        Connection connection = DBOpenHelper.getConnection();
        List<CollectionInfo> collectionInfos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CollectionInfo collectionInfo = new CollectionInfo();

                collectionInfo.setActivityId(rs.getString("activity_id"));
                collectionInfo.setActivityName(rs.getString("activity_name"));
                collectionInfo.setAcountId(rs.getString("acount_id"));
                collectionInfo.setActivityManagerId(rs.getString("activity_manager_id"));
                collectionInfo.setActivityStartTime(rs.getTimestamp("activity_start_time"));
                collectionInfo.setActivityEndTime(rs.getTimestamp("activity_end_time"));

                collectionInfos.add(collectionInfo);

            }

            rs.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collectionInfos;

    }

    public List<CollectionInfo> getAllMyCollectionOnThis(String activityId,String acountId){
        String sql = "select * from activity_collection where activity_id = "+activityId+" and acount_id = "+acountId+"";
        Connection connection = DBOpenHelper.getConnection();
        List<CollectionInfo> collectionInfos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CollectionInfo collectionInfo = new CollectionInfo();

                collectionInfo.setActivityId(rs.getString("activity_id"));
                collectionInfo.setActivityName(rs.getString("activity_name"));
                collectionInfo.setAcountId(rs.getString("acount_id"));
                collectionInfo.setActivityManagerId(rs.getString("activity_manager_id"));
                collectionInfo.setActivityStartTime(rs.getTimestamp("activity_start_time"));
                collectionInfo.setActivityEndTime(rs.getTimestamp("activity_end_time"));

                collectionInfos.add(collectionInfo);

            }

            rs.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collectionInfos;

    }


    public void AddCollection(CollectionInfo collectionInfo){
        String sql = "insert into activity_collection(activity_id,activity_name,acount_id,activity_manager_id,activity_start_time,activity_end_time)value(?,?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1,collectionInfo.getActivityId());
            pst.setString(2,collectionInfo.getActivityName());
            pst.setString(3,collectionInfo.getAcountId());
            pst.setString(4,collectionInfo.getActivityManagerId());
            pst.setTimestamp(5, new Timestamp(collectionInfo.getActivityStartTime().getTime()));
            pst.setTimestamp(6, new Timestamp(collectionInfo.getActivityEndTime().getTime()));

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void CancelCollection(String activityId,String acountId){
        String sql = "delete from activity_collection where activity_id = "+activityId+" and acount_id = "+acountId+"";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        try {
            pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.executeUpdate();

            pst.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
