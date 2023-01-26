package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.DetailPicture;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PictureDao {

    //插入详情图片
    public void InsetDetailPicture(DetailPicture detailPicture){
        String sql = "insert into detail_picture(project_id,detail_picture)value(?,?)";
        Connection connection = DBOpenHelper.getConnection();
        PreparedStatement pst;


        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,detailPicture.getProject_id());
            pst.setString(2,detailPicture.getDetail_picture());

            pst.executeUpdate();

            pst.close();
            connection.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}
