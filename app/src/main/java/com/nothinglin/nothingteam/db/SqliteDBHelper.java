package com.nothinglin.nothingteam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//Android本地客户端中添加一个本地数据库，便于存储数据
public class SqliteDBHelper extends SQLiteOpenHelper {
    public SqliteDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库第一次被创建时调用此方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化数据库的表结构，创建一个表格detail_picture用于存储图片详情中的图片的位置
        //初始化创建数据库表
        String sql = "create table detail_picture(project_id varchar(255),detail_picture_path varchar(255))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
