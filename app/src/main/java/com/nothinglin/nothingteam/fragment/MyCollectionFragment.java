package com.nothinglin.nothingteam.fragment;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.CollectionAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.dao.HiresOrderDao;
import com.xuexiang.xpage.annotation.Page;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "我的收藏")
public class MyCollectionFragment extends BaseFragment {

    @BindView(R.id.collection_list_view)
    ListView mListView;

    List<CollectionInfo> collectionInfos;
    private CollectionAdapter madapter;
    private Activity mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
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
        madapter = new CollectionAdapter(mContext,collectionInfos);
        mListView.setAdapter(madapter);

    }


    public class GetAllCollectionsThread extends Thread{

        public List<CollectionInfo> collectionInfos;
        @Override
        public void run() {
            super.run();
            UserInfo userInfo = JMessageClient.getMyInfo();
            HiresOrderDao hiresOrderDao = new HiresOrderDao();
            collectionInfos = hiresOrderDao.getAllMyCollection(userInfo.getUserName());

        }
    }
}
