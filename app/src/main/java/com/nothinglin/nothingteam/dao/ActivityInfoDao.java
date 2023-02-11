package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                activityInfo.setActivityPosition(rs.getString("activity_positon"));
                activityInfo.setActivityName(rs.getString("activity_name"));
                activityInfo.setActivityAvatar(rs.getString("activity_avatar"));
                activityInfo.setActivityStartTime(rs.getString("activity_start_time"));
                activityInfo.setActivityEndTime(rs.getString("activity_end_time"));
                activityInfo.setActivityUser(rs.getString("activity_user"));
                activityInfo.setActivityDetail(rs.getString("activity_detail"));
                activityInfo.setActivityType(rs.getString("activity_type"));

                activityInfos.add(activityInfo);

            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityInfos;
    }

}
