package com.nothinglin.nothingteam.fragment.activitypages;

import static com.xuexiang.xutil.system.ClipboardUtils.getIntent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.ActivityDetailActivity;
import com.nothinglin.nothingteam.activity.CardDetailActivity;
import com.nothinglin.nothingteam.adapter.ActivityCommentExpandAdapter;
import com.nothinglin.nothingteam.adapter.CommentExpandAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.dao.ActivityDetailCommentDao;
import com.nothinglin.nothingteam.dao.ActivityOrderDao;
import com.nothinglin.nothingteam.dao.DetailCommentDao;
import com.nothinglin.nothingteam.fragment.CardDetailFragment;
import com.nothinglin.nothingteam.widget.CommentExpandableListView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.data.DateUtils;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@Page(name = "活动详情页")
public class ActivityDetailFragment extends BaseFragment {

    @BindView(R.id.detail_picture)
    ImageView mDtailPicture;
    @BindView(R.id.detail_title)
    TextView mDetailTitle;
    @BindView(R.id.detail_content)
    TextView mDetailContent;
    @BindView(R.id.detail_positon)
    TextView mDetailPosition;
    @BindView(R.id.detail_startTime)
    TextView mStartTime;
    @BindView(R.id.detail_endTime)
    TextView mEndTime;
    @BindView(R.id.detail_union)
    TextView mDetailUnion;

    @BindView(R.id.bt_activity_collection)
    Button mBtCollection;

    //输入框
    @BindView(R.id.detail_page_do_comment)
    TextView mBt_comment;
    private BottomSheetDialog dialog;
    private ActivityCommentExpandAdapter adapter;
    private List<CommentDetail> commentsList = new ArrayList<>();
    private CommentExpandableListView expandableListView;
    private List<CommentDetail> AllCommentsList = new ArrayList<>();

    private ArrayList<ActivityInfo> activityInfoList = new ArrayList<>();
    private ActivityInfo activityInfo = new ActivityInfo();

    CollectionInfo collectionInfo = new CollectionInfo();
    List<CollectionInfo> collectionInfos = new ArrayList<>();

    private static int isColection;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_detail;
    }

    @Override
    protected void initViews() {




        //获取传过来的详情页数据
        Bundle bundle = getArguments();
        activityInfoList = (ArrayList<ActivityInfo>) bundle.getSerializable("activityInfo");
        activityInfo = activityInfoList.get(0);

        UserInfo userInfo = JMessageClient.getMyInfo();
        getActivityCollectionOnthisThread getActivityCollectionOnthisThread = new getActivityCollectionOnthisThread(activityInfo.getActivityId(),userInfo.getUserName());

        try {
            getActivityCollectionOnthisThread.start();
            getActivityCollectionOnthisThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        collectionInfos = getActivityCollectionOnthisThread.collectionInfos;

        if (collectionInfos.size() != 0){
            isColection = 2;//1收藏了
            mBtCollection.setText("已订阅");
        }else {
            isColection = 1;//0没收藏
            mBtCollection.setText("活动订阅");
        }


        if (activityInfo.getActivityAvatar() != null) {
            //图片设置
            //对缩略图进行base64转码
            byte[] imageBytes = Base64.decode(activityInfo.getActivityAvatar(), Base64.DEFAULT);
            Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            mDtailPicture.setImageBitmap(decodeImage);
        }
        mDetailTitle.setText(activityInfo.getActivityName());
        mDetailContent.setText(activityInfo.getActivityDetail());
        mDetailPosition.setText(activityInfo.getActivityPosition());

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = format1.parse(activityInfo.getActivityStartTime());
            SimpleDateFormat outputFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            mStartTime.setText(outputFormat1.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date2 = format2.parse(activityInfo.getActivityEndTime());
            SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            mEndTime.setText(outputFormat2.format(date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //弹出回复编辑窗口
        mBt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化底部弹窗内容
                dialog = new BottomSheetDialog(getContext());
                View commentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail_comment_dialog_layout, null);
                final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
                final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
                dialog.setContentView(commentView);
                dialog.show();

                //确定评论
                bt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentContent = commentText.getText().toString().trim();
                        CommentDetail ActivityCommentDetail = new CommentDetail();

                        UserInfo userInfo = JMessageClient.getMyInfo();

                        ActivityCommentDetail.setUser_name(userInfo.getUserName());
                        ActivityCommentDetail.setComment_content(commentContent);

                        InsertActivityCommentThread insertActivityCommentThread = new InsertActivityCommentThread(activityInfo.getActivityId(), userInfo.getUserName(), commentContent);

                        try {

                            insertActivityCommentThread.start();
                            insertActivityCommentThread.join();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        commentsList.add(ActivityCommentDetail);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });

            }
        });


        //初始化评论模块
        initComments();

        mBtCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isColection % 2 == 1){

                    UserInfo userInfo = JMessageClient.getMyInfo();
                    collectionInfo.setAcountId(userInfo.getUserName());//我订阅了这个活动
                    collectionInfo.setActivityId(activityInfo.getActivityId());
                    collectionInfo.setActivityName(activityInfo.getActivityName());
                    collectionInfo.setActivityManagerId(activityInfo.getActivityManagerId());


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = sdf.parse(String.valueOf(activityInfo.getActivityStartTime()));
                        collectionInfo.setActivityStartTime(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        date2 = sdf.parse(String.valueOf(activityInfo.getActivityEndTime()));
                        collectionInfo.setActivityEndTime(date2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    InsertActivityCollectionThread insertActivityCollectionThread = new InsertActivityCollectionThread(collectionInfo);

                    try {
                        insertActivityCollectionThread.join();
                        insertActivityCollectionThread.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    mBtCollection.setText("已订阅");
                    Toast.makeText(getActivity(), "您已订阅了该活动~", Toast.LENGTH_SHORT).show();

                }else {

                    mBtCollection.setText("活动订阅");

                    UserInfo userInfo = JMessageClient.getMyInfo();

                    CancelCollectionThread cancelCollectionThread = new CancelCollectionThread(activityInfo.getActivityId(),userInfo.getUserName());

                    try {
                        cancelCollectionThread.start();
                        cancelCollectionThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), "您已取消了该活动~", Toast.LENGTH_SHORT).show();

                }



                isColection++;
            }
        });

    }

    private void initComments() {
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        try {
            //初始化评论区的数据
            generateData();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initExpandableListView(commentsList);

    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     *
     * @return 评论数据
     */
    private void generateData() throws InterruptedException {

        GetActivityComments getActivityComments = new GetActivityComments();
        getActivityComments.start();
        getActivityComments.join();


    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetail> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new ActivityCommentExpandAdapter(getContext(), commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }


    }

    public class GetActivityComments extends Thread {
        @Override
        public void run() {
            ActivityDetailCommentDao activityDetailCommentDao = new ActivityDetailCommentDao();
            AllCommentsList = activityDetailCommentDao.getAllComment();

            for (CommentDetail commentDetail : AllCommentsList) {
                if (commentDetail.getProject_id().equals(activityInfo.getActivityId())) {
                    commentsList.add(commentDetail);
                }
            }
        }
    }


    public class InsertActivityCommentThread extends Thread {
        private String project_id;
        private String user_name;
        private String content;

        InsertActivityCommentThread(String project_id, String user_name, String content) {
            this.project_id = project_id;
            this.user_name = user_name;
            this.content = content;
        }

        @Override
        public void run() {

            new ActivityDetailCommentDao().InsetComment(project_id, user_name, null, content);

        }
    }


    public class InsertActivityCollectionThread extends Thread {
        CollectionInfo collectionInfo = new CollectionInfo();

        public InsertActivityCollectionThread(CollectionInfo collectionInfo) {
            this.collectionInfo = collectionInfo;
        }

        @Override
        public void run() {
            super.run();

            ActivityOrderDao activityOrderDao = new ActivityOrderDao();
            activityOrderDao.AddCollection(collectionInfo);

        }
    }

    public class getActivityCollectionOnthisThread extends Thread{
        String activityId;
        String acountId;
        public List<CollectionInfo> collectionInfos = new ArrayList<>();

        public getActivityCollectionOnthisThread(String activityId,String acountId){
            this.activityId = activityId;
            this.acountId = acountId;
        }


        @Override
        public void run() {
            super.run();

            ActivityOrderDao activityOrderDao = new ActivityOrderDao();
            collectionInfos = activityOrderDao.getAllMyCollectionOnThis(activityId,acountId);
        }
    }


    public class CancelCollectionThread extends Thread{
        String activityId;
        String acountId;

        public CancelCollectionThread(String activityId,String acountId){
            this.activityId = activityId;
            this.acountId = acountId;
        }

        @Override
        public void run() {
            super.run();

            ActivityOrderDao activityOrderDao = new ActivityOrderDao();
            activityOrderDao.CancelCollection(activityId,acountId);
        }
    }
}
