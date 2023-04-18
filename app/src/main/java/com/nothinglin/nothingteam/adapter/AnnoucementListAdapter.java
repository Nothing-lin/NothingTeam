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
import com.nothinglin.nothingteam.activity.CardDetailActivity;
import com.nothinglin.nothingteam.bean.AnnoucementBean;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.bean.HiresInfos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnnoucementListAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<AnnoucementBean> annoucementBeans;

    public AnnoucementListAdapter(Activity context, List<AnnoucementBean> annoucementBeans) {
        this.mContext = context;
        this.annoucementBeans = annoucementBeans;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return annoucementBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return annoucementBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AnnoucementBean annoucementBean = new AnnoucementBean();
        annoucementBean = annoucementBeans.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_annoucement_list_item, null);

            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.time = convertView.findViewById(R.id.time);
            holder.linearLayout = convertView.findViewById(R.id.ll_activity_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(annoucementBean.getTitle());
        holder.content.setText(annoucementBean.getContent());
        holder.time.setText(annoucementBean.getTime());


        return convertView;
    }


    class ViewHolder {
        TextView title;
        TextView content;
        TextView time;
        LinearLayout linearLayout;
    }




}
