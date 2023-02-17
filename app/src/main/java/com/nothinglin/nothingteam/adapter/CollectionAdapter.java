package com.nothinglin.nothingteam.adapter;

import static com.xuexiang.xutil.app.ActivityUtils.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.CardDetailActivity;
import com.nothinglin.nothingteam.bean.CollectionInfo;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.bean.TeamLabel;
import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.dao.VerificationInfoDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class CollectionAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<CollectionInfo> collectionInfos;

    public CollectionAdapter(Activity context, List<CollectionInfo> collectionInfos) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo = collectionInfos.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_item_collection, null);
            holder.project_name = convertView.findViewById(R.id.project_name);
            holder.linearLayout = convertView.findViewById(R.id.collection_linearlayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.project_name.setText(collectionInfo.getActivityName());

        String projectID = collectionInfo.getActivityId();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getProjectDetailThread getProjectDetailThread = new getProjectDetailThread(projectID);

                try {
                    getProjectDetailThread.start();
                    getProjectDetailThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<HiresInfos> hiresInfos =new ArrayList<>();
                hiresInfos = getProjectDetailThread.hiresInfosList;

                Intent intent = new Intent();
                intent.putExtra("detailCardInfo", (Serializable) hiresInfos);
                intent.setClass(v.getContext(), CardDetailActivity.class);
                startActivity(intent);



            }
        });



        return convertView;
    }





    class ViewHolder {
        TextView project_name;
        LinearLayout linearLayout;
    }

    public class getProjectDetailThread extends Thread{

        public ArrayList<HiresInfos> hiresInfosList = new ArrayList<>();
        private String projectID;

        public getProjectDetailThread(String projectID){
            this.projectID = projectID;
        }

        @Override
        public void run() {
            super.run();

            HiresInfosDao hiresInfosDao = new HiresInfosDao();
            hiresInfosList = hiresInfosDao.getHiresInfoThis(projectID);
            List<HiresInfosTabs> hiresInfosTabsList = hiresInfosDao.getHiresInfoTabsAll();
            List<TeamLabel> teamLabelsList = hiresInfosDao.getTeamLabelsAll();

            //将标签送入信息列表中，这样后面ToolTabCardListFragment --> CardViewListAdapter 循环传值的时候才方便
            for (HiresInfos info : hiresInfosList){
                info.setTabs(hiresInfosTabsList);
            }

            for (HiresInfos info : hiresInfosList){
                info.setTeamLabels(teamLabelsList);
            }


        }
    }


}
