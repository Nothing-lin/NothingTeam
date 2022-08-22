package com.nothinglin.nothingteam.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.SQLException;

public class UserInfoDao {

    public void setUserInfo(String userid,String password){
        String sql = "insert into user_infos(userid,password) value(?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;
        try {
            pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1,userid);
            pst.setString(2,password);

            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
