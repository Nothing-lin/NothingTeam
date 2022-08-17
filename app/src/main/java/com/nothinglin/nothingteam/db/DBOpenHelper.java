package com.nothinglin.nothingteam.db;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {

    //5.7mysql驱动名
    private static String diver = "com.mysql.jdbc.Driver";
    //数据库地址
    private static String url = "jdbc:mysql://192.168.0.161:3306/nothingteam?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //mysql用户名
    private static String user = "androidmysql";
    //mysql用户密码
    private static String password = "1353695200";


    /**
     * 调用启动数据库连接
     */

    public static Connection getConnection() {
        //装载数据库连接成功后从数据库获取的信息
        Connection connection = null;

        try {
            //返回与给定的字符串名称相关联 类 或 接口 的Class对象
            //Class.forName 传入 com.mysql.jdbc.Driver 之后,就知道我连接的数据库是 mysql
            Class.forName(diver);
            connection = (Connection) DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
