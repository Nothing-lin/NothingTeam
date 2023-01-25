package com.nothinglin.nothingteam;

import org.junit.Test;

import static org.junit.Assert.*;

import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.dao.UserInfoDao;
import com.umeng.commonsdk.debug.D;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testAddMysql(){
        UserInfoDao userInfoDao = new UserInfoDao();
        userInfoDao.setUserInfo("11","22");
    }


    @Test
    public void insertTest(){

        HiresInfos hiresInfos = new HiresInfos();
        hiresInfos.setProject_id("1234567890");

        HiresInfosDao hiresInfosDao = new HiresInfosDao();
        hiresInfosDao.InsertHiresInfo(hiresInfos);

    }

    @Test
    public void TestTime(){
        Date datetime = new Date();
        System.out.println(datetime);

    }
}