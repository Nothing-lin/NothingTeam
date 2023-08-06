package com.nothinglin.nothingteam.dao;

import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.AdvReplyBean;
import com.nothinglin.nothingteam.bean.AnnoucementBean;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnnoucementDao {

    public List<AnnoucementBean> getAnnoucementAll(){
        String sql = "select * from announcement";
        Connection connection = DBOpenHelper.getConnection();
        List<AnnoucementBean> Annoucements = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                AnnoucementBean Annoucement = new AnnoucementBean();

                Annoucement.setTitle(rs.getString("title"));
                Annoucement.setContent(rs.getString("content"));
                Annoucement.setTime(rs.getString("time"));

                Annoucements.add(Annoucement);

            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Annoucements;
    }


    public List<AdvReplyBean> getAdvReplyAll(String userid){
        String sql = "select * from index_to_list_reply where userid = "+userid;
        Connection connection = DBOpenHelper.getConnection();
        List<AdvReplyBean> advReplyBeans = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                AdvReplyBean advReplyBean = new AdvReplyBean();

                advReplyBean.setUserid(rs.getString("userid"));
                advReplyBean.setReply(rs.getString("reply"));
                advReplyBean.setProject_name(rs.getString("project_name"));

                advReplyBeans.add(advReplyBean);

            }

            rs.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advReplyBeans;
    }
}
