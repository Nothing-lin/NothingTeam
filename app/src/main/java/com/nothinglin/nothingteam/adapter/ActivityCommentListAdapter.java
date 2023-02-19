package com.nothinglin.nothingteam.adapter;

import static com.xuexiang.xutil.app.ActivityUtils.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.ActivityDetailActivity;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.CommentDetail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityCommentListAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<CommentDetail> collectionInfos;

    public ActivityCommentListAdapter(Activity context, List<CommentDetail> collectionInfos) {
        this.mContext = context;
        this.collectionInfos = collectionInfos;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return collectionInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return collectionInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentDetail collectionInfo = new CommentDetail();
        collectionInfo = collectionInfos.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_comment_activity_list_item, null);

            holder.UserName = convertView.findViewById(R.id.userName);
            holder.Content = convertView.findViewById(R.id.comment_content);
            holder.Time = convertView.findViewById(R.id.commentTime);
            holder.linearLayout = convertView.findViewById(R.id.ll_activity_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.UserName.setText(collectionInfo.getUser_name());
        holder.Content.setText(collectionInfo.getComment_content());
        SimpleDateFormat outputFormat1 = new SimpleDateFormat("MM月dd日HH时mm分ss时");
        holder.Time.setText(outputFormat1.format(collectionInfo.getComment_time()));

        CommentDetail finalCollectionInfo = collectionInfo;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCollectionAdapter.getCollectionDetailThread getCollectionDetailThread = new ActivityCollectionAdapter.getCollectionDetailThread(finalCollectionInfo.getProject_id());

                try {
                    getCollectionDetailThread.start();
                    getCollectionDetailThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<ActivityInfo> activityInfos = new ArrayList<>();
                activityInfos = getCollectionDetailThread.activityInfoList;

                Intent intent = new Intent();
                intent.putExtra("activityInfo", (Serializable) activityInfos);
                intent.setClass(v.getContext(), ActivityDetailActivity.class);
                startActivity(intent);

            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView UserName;
        TextView Content;
        TextView Time;
        LinearLayout linearLayout;
    }

}
