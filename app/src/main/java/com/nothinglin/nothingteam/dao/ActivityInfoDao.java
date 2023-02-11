package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.ActivityInfo;
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

public class ActivityInfoDao {

    public List<ActivityInfo> getActivityInfosAll(){
        String sql = "select * from activity_infos";
        Connection connection = DBOpenHelper.getConnection();
        List<ActivityInfo> activityInfos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                ActivityInfo activityInfo = new ActivityInfo();

                activityInfo.setActivityId(rs.getString("activity_id"));
                activityInfo.setActivityPosition(rs.getString("activity_position"));
                activityInfo.setActivityName(rs.getString("activity_name"));
                activityInfo.setActivityAvatar(rs.getString("activity_avatar"));
                activityInfo.setActivityStartTime(rs.getString("activity_start_time"));
                activityInfo.setActivityEndTime(rs.getString("activity_end_time"));
                activityInfo.setActivityUser(rs.getString("activity_user"));
                activityInfo.setActivityDetail(rs.getString("activity_detail"));
                activityInfo.setActivityType(rs.getString("activity_type"));
                activityInfo.setActivityManagerId(rs.getString("activity_manager_id"));

                activityInfos.add(activityInfo);

            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityInfos;
    }

    public void InsertActivityInfo(ActivityInfo activityInfo){
        String sql = "insert into activity_infos(activity_position,activity_name,activity_avatar,activity_user,activity_detail,activity_type,activity_start_time,activity_end_time,activity_manager_id)value(?,?,?,?,?,?,?,?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;

        //日期转换
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(activityInfo.getActivityStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(activityInfo.getActivityEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }



        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,activityInfo.getActivityPosition());
            pst.setString(2, activityInfo.getActivityName());
            pst.setString(3, activityInfo.getActivityAvatar());
            pst.setString(4, activityInfo.getActivityUser());
            pst.setString(5, activityInfo.getActivityDetail());
            pst.setString(6, activityInfo.getActivityType());
            pst.setTimestamp(7,new Timestamp(date1.getTime()));
            pst.setTimestamp(8,new Timestamp(date2.getTime()));
            pst.setString(9,activityInfo.getActivityManagerId());

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
