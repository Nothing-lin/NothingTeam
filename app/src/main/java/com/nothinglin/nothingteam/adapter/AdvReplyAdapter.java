package com.nothinglin.nothingteam.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.bean.AdvReplyBean;
import com.nothinglin.nothingteam.bean.AnnoucementBean;

import java.util.List;

public class AdvReplyAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<AdvReplyBean> advReplyBeans;

    public AdvReplyAdapter(Activity context, List<AdvReplyBean> advReplyBeans) {
        this.mContext = context;
        this.advReplyBeans = advReplyBeans;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return advReplyBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return advReplyBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AdvReplyBean advReplyBean = new AdvReplyBean();
        advReplyBean = advReplyBeans.get(position);

        //如果不存在视图，则初始化视图
        AdvReplyAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new AdvReplyAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_adv_list_item, null);

            holder.project = convertView.findViewById(R.id.project);
            holder.reply = convertView.findViewById(R.id.reply);
            holder.linearLayout = convertView.findViewById(R.id.ll_activity_collection);

            convertView.setTag(holder);
        } else {
            holder = (AdvReplyAdapter.ViewHolder) convertView.getTag();
        }

        holder.project.setText(advReplyBean.getProject_name());
        holder.reply.setText(advReplyBean.getReply());


        return convertView;
    }


    class ViewHolder {
        TextView project;
        TextView reply;
        LinearLayout linearLayout;
    }



}
