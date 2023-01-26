package com.nothinglin.nothingteam.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.donkingliang.labels.LabelsView;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.bean.ToolTabCardInfo;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.widget.DiffUtilCallback;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.common.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XUE
 * @since 2019/5/9 10:41
 */
public class CardViewListAdapter extends BaseRecyclerAdapter<HiresInfos> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.fragment_home_adapter_card_view_list_item;
    }

    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, int position, HiresInfos model) {


        if (model != null) {
            holder.text(R.id.tv_title, model.getProject_name());
            holder.text(R.id.tv_user_name, model.getProject_owner_team_name());
            holder.text(R.id.tv_team_goal, model.getCompetition_type());
            holder.text(R.id.tv_menbers, model.getHire_numbers());
            holder.text(R.id.tv_position, model.getProject_position());
            holder.text(R.id.tv_summary, model.getProject_introdution());
            holder.text(R.id.rb_project_type,model.getProject_type());

            if (model.getTeam_avatar() != null){
                //对头像进行base64转码
                //头像处理，对头像图片进行转码
                byte[] imageBytes = Base64.decode(model.getTeam_avatar(),Base64.DEFAULT);
                Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                //将bitmap转成drawble
                Drawable drawableImage = new BitmapDrawable(decodeImage);
                holder.image(R.id.iv_avatar,drawableImage);

            }

            LabelsView labelsView = holder.findViewById(R.id.labels);

            ArrayList<String> tablist = new ArrayList<>();

            /**
             * 注意这里比较不能model.getProject_id() == model.getTabs().get(i).getProject_id()
             * 这两个数值虽然是一样，但是映射机制不一样，== 是表示全部都一样，映射机制都要一样
             * 而使用x.equals()方法，只是比较值是否一样，不考虑映射机制
             * 这里是一个坑，让我 思考了很久
             */

            for (int i =0;i < model.getTabs().size();i++){
                if (model.getProject_id().equals(model.getTabs().get(i).getProject_id())){
                    tablist.add(model.getTabs().get(i).getAbility_requirements());
                }
            }


            labelsView.setLabels(tablist);
            labelsView.setSelectType(LabelsView.SelectType.NONE);


//            holder.visible(R.id.rb_tab1,-1);
//            holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
//            holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
//            holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//            holder.image(R.id.iv_image, model.getImageUrl());
        }

        
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (CollectionUtils.isEmpty(payloads)) {
            Logger.e("正在进行全量刷新:" + position);
            onBindViewHolder(holder, position);
            return;
        }
        // payloads为非空的情况，进行局部刷新

        //取出我们在getChangePayload（）方法返回的bundle
        Bundle payload = WidgetUtils.getChangePayload(payloads);
        if (payload == null) {
            return;
        }

//        Logger.e("正在进行增量刷新:" + position);
//        ToolTabCardInfo toolTabCardInfo = getItem(position);
//        for (String key : payload.keySet()) {
//            switch (key) {
//                case DiffUtilCallback.PAYLOAD_USER_NAME:
//                    //这里可以用payload里的数据，不过newInfo也是新的 也可以用
//                    holder.text(R.id.tv_user_name, toolTabCardInfo.getUserName());
//                    break;
//                case DiffUtilCallback.PAYLOAD_PRAISE:
//                    holder.text(R.id.tv_praise, payload.getInt(DiffUtilCallback.PAYLOAD_PRAISE) == 0 ? "点赞" : String.valueOf(payload.getInt(DiffUtilCallback.PAYLOAD_PRAISE)));
//                    break;
//                case DiffUtilCallback.PAYLOAD_COMMENT:
//                    holder.text(R.id.tv_comment, payload.getInt(DiffUtilCallback.PAYLOAD_COMMENT) == 0 ? "评论" : String.valueOf(payload.getInt(DiffUtilCallback.PAYLOAD_COMMENT)));
//                    break;
//                case DiffUtilCallback.PAYLOAD_READ_NUMBER:
////                    holder.text(R.id.tv_read, "阅读量 " + payload.getInt(DiffUtilCallback.PAYLOAD_READ_NUMBER));
//                    break;
//                default:
//                    break;
//            }
//        }
    }

}
