package com.nothinglin.nothingteam;

import org.junit.Test;

import static org.junit.Assert.*;

import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.VerificationReply;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.dao.OrderDao;
import com.nothinglin.nothingteam.dao.UserInfoDao;
import com.nothinglin.nothingteam.dao.VerificationInfoDao;
import com.umeng.commonsdk.debug.D;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        List<CollectionInfo> collectionInfos =new ArrayList<>();
        OrderDao orderDaos = new OrderDao();
//        collectionInfos = orderDaos.getAllMyCollectionOnThis("1","1");
//        System.out.println(collectionInfos);

        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo.setAcountId("12");
        collectionInfo.setActivityName("12");
        collectionInfo.setActivityManagerId("12");
        collectionInfo.setActivityId("32");

//        orderDaos.AddCollection(collectionInfo);

        orderDaos.CancelCollection("32","12");

    }
}