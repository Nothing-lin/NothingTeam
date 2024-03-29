package com.nothinglin.nothingteam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.bean.CommentDetail;
import com.nothinglin.nothingteam.dao.ActivityDetailCommentDao;
import com.nothinglin.nothingteam.dao.DetailCommentDao;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class ActivityCommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentDetail> commentBeanList;
    private Context context;
    private int pageIndex = 1;

    public ActivityCommentExpandAdapter(Context context, List<CommentDetail> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_detail_comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(R.drawable.ic_cat)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(groupHolder.logo);
        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getUser_name());
//        groupHolder.tv_time.setText(commentBeanList.get(groupPosition).getCreateDate());
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getComment_content());

        //评论的删除
        UserInfo userInfo = JMessageClient.getMyInfo();
        if (commentBeanList.get(groupPosition).getUser_name().equals(userInfo.getUserName())) {
            groupHolder.comment_delete.setVisibility(View.VISIBLE);
        } else {
            groupHolder.comment_delete.setVisibility(View.GONE);
        }

        groupHolder.comment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectId = commentBeanList.get(groupPosition).getProject_id();
                String userName = commentBeanList.get(groupPosition).getUser_name();
                String content = commentBeanList.get(groupPosition).getComment_content();

                DeleteCommentThread deleteComment = new DeleteCommentThread(projectId,userInfo.getUserName(),content);

                try {

                    deleteComment.start();
                    deleteComment.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                commentBeanList.remove(commentBeanList.get(groupPosition));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private TextView comment_delete;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            comment_delete = view.findViewById(R.id.comment_delete);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetail 新的评论数据
     */
    public void addTheCommentData(CommentDetail commentDetail) {
        if (commentDetail != null) {

            commentBeanList.add(commentDetail);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    public class DeleteCommentThread extends Thread{
        private String project_id;
        private String user_name;
        private String content;

        DeleteCommentThread(String project_id,String user_name,String content){
            this.project_id = project_id;
            this.user_name = user_name;
            this.content = content;
        }

        @Override
        public void run() {

            ActivityDetailCommentDao activityDetailCommentDao = new ActivityDetailCommentDao();

            activityDetailCommentDao.DeleteComment(project_id,user_name,content);

        }
    }

}
