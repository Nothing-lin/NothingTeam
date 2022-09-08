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
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class SingleChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;//聊天界面标题栏
    private ListView mList;//聊天界面中的item
    private ChatAdapter mAdapter;//聊天界面消息适配器
    private List<Message> mData;//消息容器
    private EditText mEt_input;//聊天界面中的输入框
    private Button mBt_send;//聊天界面中的消息发送按钮
    private String username;

    //数据传递监听器
    private ChatAdapter.MyClickListener mListener = new ChatAdapter.MyClickListener(){
        @Override
        public void myOnClick(int p, View w) {
            String u = mData.get(p).getFromUser().getUserName();//获取对应位置上的用户名
            Intent i = new Intent();

            i.putExtra("u",u);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册极光的接收事务
//        JMessageClient.registerEventReceiver(this);

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
                    //单人聊天信息通道
                    Message message = JMessageClient.createSingleTextMessage(username,text);
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
        String n = null;

        if (i.getStringExtra("username") != null){
            username = i.getStringExtra("username");//消息列表用户名
            n = i.getStringExtra("name");
        }
        if (i.getStringExtra("teamUserName") != null){
            username = i.getStringExtra("teamUserId");//联系团队的用户名
            n = i.getStringExtra("teamUserName");
        }


        mToolbar.setTitle(n);//显示对话用户名称
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
        JMessageClient.enterSingleConversation(username);



    }

    private void getHistory() {
        Conversation conversation = JMessageClient.getSingleConversation(username,null);

        if (conversation == null){
            return;//如果没有聊天消息直接返回
        }

        //获取过去近50条记录信息
        List<Message> messageList = conversation.getMessagesFromOldest(conversation.getLatestMessage().getId()-50,50);

        for (Message message : messageList){
            mData.add(message);
            mAdapter.notifyDataSetChanged();//通知适配器信息内容发生了改变、动态刷新
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
        Conversation conversation = JMessageClient.getSingleConversation(username,null);
        //如果暂停单聊的页面是存在未读消息，那么设置为已读
        if (conversation != null){
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
        //注册消息事务
        Message message = event.getMessage();
        //如果当前的消息不属于单聊的话直接结束
        if (message.getTargetType() != ConversationType.single){
            return;
        }

        //如果消息不是目标对象发来的话那么就结束
        if (!message.getFromUser().getUserName().equals(username)){
            return;
        }
        mData.add(message);
        //热加载消息界面
        mAdapter = new ChatAdapter(this,mData,mListener);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mList.setAdapter(mAdapter);//热显示聊天界面的消息列表
                mAdapter.notifyDataSetChanged();
                mList.setSelection(mAdapter.getCount());
            }
        });

    }
}
