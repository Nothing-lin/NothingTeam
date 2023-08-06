package com.nothinglin.nothingteam.fragment.annoucement;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.AdvReplyAdapter;
import com.nothinglin.nothingteam.adapter.AnnoucementListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.AdvReplyBean;
import com.nothinglin.nothingteam.dao.AnnoucementDao;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class AdvReplyFragment extends BaseFragment {

    @BindView(R.id.collection_list_view)
    ListView mListView;

    List<AdvReplyBean> advReplyBeans;
    private AdvReplyAdapter madapter;
    private Activity mContext;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adv_item_list;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {


        getAllAdvThread getAllAdvThread = new getAllAdvThread();

        try {
            getAllAdvThread.start();
            getAllAdvThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        advReplyBeans = getAllAdvThread.advReplyBeans;

        mContext = getActivity();
        madapter = new AdvReplyAdapter(mContext,advReplyBeans);
        mListView.setAdapter(madapter);

    }

    public class getAllAdvThread extends Thread{
        public List<AdvReplyBean> advReplyBeans = new ArrayList<>();

        @Override
        public void run() {
            super.run();

            UserInfo userInfo = JMessageClient.getMyInfo();

            AnnoucementDao annoucementDao = new AnnoucementDao();
            advReplyBeans = annoucementDao.getAdvReplyAll(userInfo.getUserName());


        }
    }

}
