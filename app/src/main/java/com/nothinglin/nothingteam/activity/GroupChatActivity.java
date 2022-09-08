package com.nothinglin.nothingteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;//聊天界面标题栏
    private ListView mList;//聊天界面中的item
    private ChatAdapter mAdapter;//聊天界面消息适配器
    private List<Message> mData;//消息容器
    private EditText mEt_input;//聊天界面中的输入框
    private Button mBt_send;//聊天界面中的消息发送按钮
    private long GroupId;

    //数据传递监听器
    private ChatAdapter.MyClickListener mListener = new ChatAdapter.MyClickListener(){
        @Override
        public void myOnClick(int p, View w) {
//            String u = mData.get(p).getFromUser().getUserName();
//            Intent i = new Intent();
//            if (u.equals("系统消息")) {
//                return;
//            }
//            if (u.equals(JMessageClient.getMyInfo().getUserName())) {
//                i.setClass(GroupChatActivity.this, MyInfo.class);
//            } else {
//                i.putExtra("u", u);
//                i.putExtra("b", 1);
//                i.setClass(GroupChatActivity.this, GetUserInfo.class);
//            }
//            startActivity(i);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册视图
        intiView();

        //注册数据
        initData();

        //注册监听事件
        initListener();

    }

    private void initListener() {
        //发送按钮的监听事件
        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = mEt_input.getText().toString();
                //如果输入框中有值，输入框不为空
                if (!TextUtils.isEmpty(text)){
                    //群聊信息通道
                    Message message = JMessageClient.createGroupTextMessage(GroupId,text);
                    //调用极光服务器的回调方法
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            //发送成功
                            if (i == 0){
                                mAdapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getApplicationContext(),"发送失败"+s,Toast.LENGTH_SHORT).show();
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                    //发送信息
                    JMessageClient.sendMessage(message);
                    mData.add(message);
                    mAdapter.notifyDataSetChanged();
                    mEt_input.setText("");
                    //?重置list数量？
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mList.setSelection(mAdapter.getCount());
                        }
                    },100);
                }

            }
        });

    }

    private void initData() {
        setSupportActionBar(mToolbar);
        //标题栏的title
        getSupportActionBar().setTitle("");

        //数据传递
        Intent i = getIntent();
        GroupId = i.getLongExtra("GroupId",0);
        String GroupName = i.getStringExtra("GroupName");

        mToolbar.setTitle(GroupName);//显示对话用户名称
        //返回←
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mData = new ArrayList<>();
        mAdapter = new ChatAdapter(this,mData,mListener);//自动监听信息变化mlistener
        mList.setAdapter(mAdapter);//封装适配的消息布局到内容列表上

        //获取历史聊天记录
        getHistory();

        //获取对应的聊天对话内容
        JMessageClient.enterGroupConversation(GroupId);



    }

    private void getHistory() {
        Conversation conversation = JMessageClient.getGroupConversation(GroupId);
        if (conversation == null) {
            return;
        }
        List<Message> messageList = conversation.getMessagesFromOldest(
                conversation.getLatestMessage().getId() - 50, 50);

        for (Message message : messageList) {
            mData.add(message);
            mAdapter.notifyDataSetChanged();
            mList.setSelection(mAdapter.getCount());

        }

    }


    private void intiView() {
        setContentView(R.layout.activity_chat);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        mList = (ListView) findViewById(R.id.lv_chat);
        mEt_input = (EditText) findViewById(R.id.et_chat_input);
        mBt_send = (Button) findViewById(R.id.bt_chat_send);
    }


    //程序正常启动时：onCreate()->onStart()->onResume();
    // App用到一半 有事Home键切出去了 在回来时调onResume
    //应该是监听消息内容的变化
    @Override
    public void onResume() {
        super.onResume();
        //当sdk收到某些后台下发的数据，或者发生了某些需要上层关注的事件时，sdk会上抛事件对象通知给上层，例如，在线消息事件、会话刷新事件、用户下线事件等。
        JMessageClient.registerEventReceiver(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Conversation conversation = JMessageClient.getGroupConversation(GroupId);
        if (conversation != null) {
            conversation.resetUnreadCount();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出单聊界面时，调用极光服务器说明已经退出聊天
        JMessageClient.exitConversation();
        //取消消息接收的监听
        JMessageClient.unRegisterEventReceiver(this);
        //结束当前页面
        finish();
    }

    public void onEvent(MessageEvent event){
        Message message = event.getMessage();
        if (message.getTargetType() != ConversationType.group) {
            return;
        }

        GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
        if (groupInfo.getGroupID() != GroupId) {
            return;
        }
        mData.add(message);
        mAdapter = new ChatAdapter(this, mData, mListener);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mList.setSelection(mAdapter.getCount());
            }
        });

    }
}
