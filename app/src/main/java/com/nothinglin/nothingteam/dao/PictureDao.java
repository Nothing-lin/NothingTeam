package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.DetailPicture;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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


    //获取详情图片
    public List<DetailPicture> getDetailPicture(String project_id){
        String sql = "select * from detail_picture where project_id = "+ project_id;
        Connection connection = DBOpenHelper.getConnection();
        List<DetailPicture> detailPictures = new ArrayList<>();

        Statement statement = null;
        try {
            statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                DetailPicture detailPicture = new DetailPicture();

                detailPicture.setProject_id(rs.getString("project_id"));
                detailPicture.setDetail_picture(rs.getString("detail_picture"));

                detailPictures.add(detailPicture);

            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return detailPictures;

    }



}
