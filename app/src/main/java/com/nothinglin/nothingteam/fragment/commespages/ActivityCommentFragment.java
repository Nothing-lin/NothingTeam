package com.nothinglin.nothingteam.fragment.commespages;

import android.app.Activity;
import android.widget.ListView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ActivityCommentListAdapter;
import com.nothinglin.nothingteam.adapter.ProjectCommentListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.dao.ActivityDetailCommentDao;
import com.nothinglin.nothingteam.dao.DetailCommentDao;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;


public class ActivityCommentFragment extends BaseFragment {
    @BindView(R.id.collection_list_view)
    ListView mListView;

    List<CommentDetail> collectionInfos;
    private ActivityCommentListAdapter madapter;
    private Activity mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_activity_list;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {

        UserInfo userInfo = JMessageClient.getMyInfo();
        ActivityCommentFragment.getAllmyCommentsThread getAllmyCommentsThread = new ActivityCommentFragment.getAllmyCommentsThread(userInfo.getUserName());

        try {
            getAllmyCommentsThread.start();
            getAllmyCommentsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        collectionInfos = getAllmyCommentsThread.commentDetails;

        mContext = getActivity();
        madapter = new ActivityCommentListAdapter(mContext,collectionInfos);
        mListView.setAdapter(madapter);

    }

    public class getAllmyCommentsThread extends Thread{
        public List<CommentDetail> commentDetails = new ArrayList<>();
        private String managerid;

        public getAllmyCommentsThread(String managerid){
            this.managerid = managerid;
        }

        @Override
        public void run() {
            super.run();

            ActivityDetailCommentDao activityDetailCommentDao = new ActivityDetailCommentDao();

            List<CommentDetail> temp = new ArrayList<>();
            temp = activityDetailCommentDao.getAllMyComment(managerid);

            UserInfo userInfo = JMessageClient.getMyInfo();



            for (CommentDetail commentDetail : temp){
                boolean isequals = commentDetail.getUser_name().equals(userInfo.getUserName());
                if (!isequals){
                    commentDetails.add(commentDetail);
                }
            }


        }
    }



}
