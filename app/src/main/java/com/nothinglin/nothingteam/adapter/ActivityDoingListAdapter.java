package com.nothinglin.nothingteam.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.bean.ActivityInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityDoingListAdapter extends BaseAdapter {
    private Activity mContext;
    private final LayoutInflater mInflater;
    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    public ActivityDoingListAdapter(Activity mContext, List<ActivityInfo> activityInfoList) {
        this.mContext = mContext;
        this.activityInfoList = activityInfoList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return activityInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo = activityInfoList.get(position);

        //如果视图不存在则初始化视图
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_activity_adapter_card_view_list_item,null);
            holder.item_position = convertView.findViewById(R.id.activity_positon);
            holder.item_name = convertView.findViewById(R.id.activity_title);
            holder.item_image = convertView.findViewById(R.id.activity_picture);
            holder.item_user = convertView.findViewById(R.id.activity_union);
            holder.item_type = convertView.findViewById(R.id.activity_type);
            holder.item_startTime = convertView.findViewById(R.id.activity_startTime);
            holder.item_endTime = convertView.findViewById(R.id.activity_endTime);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_position.setText(activityInfo.getActivityPosition());
        holder.item_name.setText(activityInfo.getActivityName());
        holder.item_user.setText(activityInfo.getActivityUser());
        //日期设置

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date1 = format1.parse(activityInfo.getActivityStartTime());
            SimpleDateFormat outputFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            holder.item_startTime.setText(outputFormat1.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date2 = format2.parse(activityInfo.getActivityEndTime());
            SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            holder.item_endTime.setText(outputFormat2.format(date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.item_startTime.setText(activityInfo.getActivityStartTime());
        holder.item_endTime.setText(activityInfo.getActivityEndTime());

        if (activityInfo.getActivityAvatar()== null){
            holder.item_image.setImageResource(R.drawable.birthday);

        }else {
            //对缩略图进行base64转码
            byte[] imageBytes = Base64.decode(activityInfo.getActivityAvatar(),Base64.DEFAULT);
            Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            holder.item_image.setImageBitmap(decodeImage);
        }

        holder.item_type.setText(activityInfo.getActivityType());




        return convertView;
    }


    class ViewHolder {
        TextView item_position;
        TextView item_name;
        ImageView item_image;
        TextView item_startTime;
        TextView item_endTime;
        TextView item_user;
        TextView item_type;
    }
}
