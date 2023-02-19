package com.nothinglin.nothingteam.adapter;

import static com.xuexiang.xutil.app.ActivityUtils.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.ActivityDetailActivity;
import com.nothinglin.nothingteam.activity.CardDetailActivity;
import com.nothinglin.nothingteam.bean.ActivityInfo;
import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.dao.ActivityInfoDao;
import com.nothinglin.nothingteam.dao.HiresInfosDao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityCollectionAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<CollectionInfo> collectionInfos;

    public ActivityCollectionAdapter(Activity context, List<CollectionInfo> collectionInfos) {
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

        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo = collectionInfos.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_activity_collection_item, null);

            holder.activity_name = convertView.findViewById(R.id.activity_name);
            holder.start_time = convertView.findViewById(R.id.start_time);
            holder.end_time = convertView.findViewById(R.id.end_time);
            holder.activity_status = convertView.findViewById(R.id.activity_status);
            holder.linearLayout = convertView.findViewById(R.id.ll_activity_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat outputFormat1 = new SimpleDateFormat("MM月dd日HH时");
        holder.start_time.setText(outputFormat1.format(collectionInfo.getActivityStartTime()));
        holder.end_time.setText(outputFormat1.format(collectionInfo.getActivityEndTime()));

        holder.activity_name.setText(collectionInfo.getActivityName());

        Date currentDate = Calendar.getInstance().getTime();

        int compareDateResult1 = currentDate.compareTo(collectionInfo.getActivityStartTime());
        int compareDateResult2 = currentDate.compareTo(collectionInfo.getActivityEndTime());

        if (compareDateResult1 < 0) {
            //活动还没发生
            holder.activity_status.setText("活动未开始");
            holder.activity_status.setBackgroundResource(R.drawable.status_blue);
        } else if (compareDateResult1 > 0) {
            //活动发生了，但不知道有没结束，所以还要判断
            if (compareDateResult2 < 0) {
                //现在的时间还没超过结束时间，那么活动是正在进行中的
                holder.activity_status.setText("活动进行中");
                holder.activity_status.setBackgroundResource(R.drawable.status_yellow);
            } else {
                //现在的时间已经超过了活动的结束时间，活动已经结束
                holder.activity_status.setText("活动已结束");
                holder.activity_status.setBackgroundResource(R.drawable.status_gray);
            }

        }

        CollectionInfo finalCollectionInfo = collectionInfo;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCollectionDetailThread getCollectionDetailThread = new getCollectionDetailThread(finalCollectionInfo.getActivityId());

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
        TextView activity_name;
        TextView start_time;
        TextView end_time;
        Button activity_status;
        LinearLayout linearLayout;
    }

    public static class getCollectionDetailThread extends Thread{

        String activityId;
        public List<ActivityInfo> activityInfoList = new ArrayList<>();

        public getCollectionDetailThread(String activityId){
            this.activityId = activityId;
        }


        @Override
        public void run() {
            super.run();

            ActivityInfoDao activityInfoDao = new ActivityInfoDao();
            activityInfoList = activityInfoDao.getActivityInfosOnThis(activityId);
        }
    }

}
