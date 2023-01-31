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

import java.io.File;
import java.util.List;

public class GroupVerificationAdapter extends BaseAdapter {

    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<VerificationInfo> recommends;

    public GroupVerificationAdapter(Activity context, List<VerificationInfo> recommends) {
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

        VerificationInfo verificationInfo = new VerificationInfo();
        verificationInfo = recommends.get(position);

        //如果不存在视图，则初始化视图
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_item_group_owner_recomend, null);
            holder.item_name = convertView.findViewById(R.id.item_name);
            holder.item_reason = convertView.findViewById(R.id.tv_groupInvite);
            holder.item_image = convertView.findViewById(R.id.item_head_icon);
            holder.item_group_name = convertView.findViewById(R.id.item_reason);
            holder.item_accept = convertView.findViewById(R.id.item_add_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_name.setText(verificationInfo.getApplyUserName());
        holder.item_reason.setText(verificationInfo.getApplyText());
        holder.item_group_name.setText(verificationInfo.getGroupName());

        byte[] imageBytes = Base64.decode(verificationInfo.getAvatar(),Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        holder.item_image.setImageBitmap(decodeImage);

        holder.item_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recommends.remove(recommends.get(position));
                notifyDataSetChanged();

                Toast.makeText(mContext, "您已通过了该用户加群的请求~", Toast.LENGTH_SHORT).show();


            }
        });




        return convertView;
    }



    class ViewHolder {
        TextView item_accept;
        TextView item_name;
        TextView item_reason;
        ImageView item_image;
        TextView item_group_name;
    }
}
