package com.nothinglin.nothingteam.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.bean.VerificationInfo;
import com.nothinglin.nothingteam.bean.VerificationReply;
import com.nothinglin.nothingteam.dao.VerificationInfoDao;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class GroupVerificationMessageAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<VerificationReply> recommends;

    public GroupVerificationMessageAdapter(Activity context, List<VerificationReply> recommends) {
        this.mContext = context;
        this.recommends = recommends;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return recommends.size();
    }

    @Override
    public Object getItem(int position) {
        return recommends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VerificationReply verificationReply = new VerificationReply();
        verificationReply = recommends.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_item_group_recomend_reply, null);
            holder.item_name = convertView.findViewById(R.id.item_name);
            holder.item_reason = convertView.findViewById(R.id.item_reason);
            holder.item_image = convertView.findViewById(R.id.item_head_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_name.setText(verificationReply.getProjectName());
        holder.item_reason.setText(verificationReply.getTips());

        if (verificationReply.getAvatar() != null) {
            byte[] imageBytes = Base64.decode(verificationReply.getAvatar(), Base64.DEFAULT);
            Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.item_image.setImageBitmap(decodeImage);
        }else {
            holder.item_image.setImageResource(R.drawable.ic_cat);
        }



        return convertView;
    }



    class ViewHolder {
        TextView item_name;
        TextView item_reason;
        ImageView item_image;
    }


}
