package com.nothinglin.nothingteam;

import android.content.Context;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.nothinglin.nothingteam.adapter.GroupListAdapter;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.DetailPicture;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.ActivityInfoDao;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.dao.PictureDao;
import com.nothinglin.nothingteam.dao.VerificationInfoDao;
import com.nothinglin.nothingteam.dao.table.HiresInfosTable;
import com.nothinglin.nothingteam.db.DBOpenHelper;
import com.nothinglin.nothingteam.utils.GlobalThreadPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.nothinglin.nothingteam", appContext.getPackageName());
    }
    
    @Test
    public void testMysql() throws SQLException {

        PictureDao pictureDao = new PictureDao();
        List<DetailPicture> list = pictureDao.getDetailPicture("1353695225");
        System.out.println(list);
    }


    @Test
    public void TestJG(){

        List<ActivityInfo> activityInfos = new ArrayList<>();
        ActivityInfoDao activityInfoDao = new ActivityInfoDao();
        activityInfos = activityInfoDao.getActivityInfosAll();
//        Date mysqlDate = new Date(activityInfos.get(0).getActivityStartTime());


        System.out.println(activityInfos);

    }
}