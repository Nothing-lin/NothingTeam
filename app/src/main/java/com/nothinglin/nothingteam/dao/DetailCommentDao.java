package com.nothinglin.nothingteam.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.dao.table.CommentDetailTable;
import com.nothinglin.nothingteam.db.DBOpenHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailCommentDao {

    public List<CommentDetail> getAllComment(){
        String sql = "select * from detail_comment";
        Connection connection = DBOpenHelper.getConnection();
        List<CommentDetail> commentDetails = new ArrayList<>();

        try {
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CommentDetail commentDetail = new CommentDetail();

                commentDetail.setProject_id(rs.getString(CommentDetailTable.COL_PROJECT_ID));
                commentDetail.setUser_name(rs.getString(CommentDetailTable.COL_USER_NAME));
                commentDetail.setComment_time(rs.getTime(CommentDetailTable.COL_COMMENT_TIME));
                commentDetail.setComment_content(rs.getString(CommentDetailTable.COL_COMMENT_CONTENT));

                commentDetails.add(commentDetail);
            }

            rs.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return commentDetails;
    }
}
