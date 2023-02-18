package com.nothinglin.nothingteam.fragment;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ActivityCollectionAdapter;
import com.nothinglin.nothingteam.adapter.CollectionAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.dao.ActivityOrderDao;
import com.nothinglin.nothingteam.dao.HiresOrderDao;
import com.xuexiang.xpage.annotation.Page;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "活动订阅")
public class ActivityCollectionFragment extends BaseFragment {

    @BindView(R.id.collection_list_view)
    ListView mListView;

    List<CollectionInfo> collectionInfos;
    private ActivityCollectionAdapter madapter;
    private Activity mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity_collection_list;
    }

    @Override
    protected void initViews() {

        GetAllCollectionsThread getAllCollectionsThread = new GetAllCollectionsThread();

        try {
            getAllCollectionsThread.start();
            getAllCollectionsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        collectionInfos = getAllCollectionsThread.collectionInfos;
        mContext = getActivity();
        madapter = new ActivityCollectionAdapter(mContext,collectionInfos);
        mListView.setAdapter(madapter);

    }


    public class GetAllCollectionsThread extends Thread{

        public List<CollectionInfo> collectionInfos;
        @Override
        public void run() {
            super.run();
            UserInfo userInfo = JMessageClient.getMyInfo();
            ActivityOrderDao activityOrderDao = new ActivityOrderDao();
            collectionInfos = activityOrderDao.getAllMyCollection(userInfo.getUserName());

        }
    }
}
