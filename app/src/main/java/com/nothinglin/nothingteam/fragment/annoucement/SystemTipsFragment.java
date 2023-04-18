package com.nothinglin.nothingteam.fragment.annoucement;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.AnnoucementListAdapter;
import com.nothinglin.nothingteam.adapter.ProjectCommentListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.AnnoucementBean;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.dao.AnnoucementDao;
import com.nothinglin.nothingteam.dao.DetailCommentDao;
import com.nothinglin.nothingteam.fragment.commespages.ProjectCommentFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class SystemTipsFragment  extends BaseFragment {
    @BindView(R.id.collection_list_view)
    ListView mListView;

    List<AnnoucementBean> AnnoucementBeans;
    private AnnoucementListAdapter madapter;
    private Activity mContext;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_annoucement_item_list;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {


        getAllAnnoucementsThread getAllAnnoucementsThread = new getAllAnnoucementsThread();

        try {
            getAllAnnoucementsThread.start();
            getAllAnnoucementsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AnnoucementBeans = getAllAnnoucementsThread.annoucementBeans;

        mContext = getActivity();
        madapter = new AnnoucementListAdapter(mContext,AnnoucementBeans);
        mListView.setAdapter(madapter);

    }

    public class getAllAnnoucementsThread extends Thread{
        public List<AnnoucementBean> annoucementBeans = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            AnnoucementDao annoucementDao = new AnnoucementDao();
            annoucementBeans = annoucementDao.getAnnoucementAll();


        }
    }

}
