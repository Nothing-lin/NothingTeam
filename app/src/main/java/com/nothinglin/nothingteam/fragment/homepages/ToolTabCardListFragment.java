package com.nothinglin.nothingteam.fragment.homepages;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.CardViewListAdapter;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.ToolTabCardInfo;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.widget.DemoDataProvider;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 这里是homepage中的tooltab部分的卡片列表中的内容显示中的数据处理和显示的部分
 * 每个tab页在这里进行设置，这个页面只负责tabview下的卡片列表的处理
 *
 * ！！下面只是测试数据，后期连接数据库要对该页面进行重写
 */
public class ToolTabCardListFragment extends BaseFragment {

    private static final String KEY_IS_SPECIAL = "key_is_special";

    //这里要定义构造函数才能获取传来的title，判断对应选项卡页面中的数据
    private String tabtitle;
    private List<HiresInfos> hiresInfosList = new ArrayList<>();

    private List<HiresInfos> toolTabCardInfos = new ArrayList<>();

    public ToolTabCardListFragment() {
    }

    public ToolTabCardListFragment(String tabtitle,List<HiresInfos> hiresInfosList) {
        this.tabtitle = tabtitle;
        this.hiresInfosList = hiresInfosList;
    }


    //初始化视图组件
    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CardViewListAdapter mAdapter;

    @AutoWired(name = KEY_IS_SPECIAL)
    boolean isSpecial;

    public static ToolTabCardListFragment newInstance(boolean isSpecial) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_SPECIAL, isSpecial);
        ToolTabCardListFragment fragment = new ToolTabCardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    不知道为什么这个会导致app运行不起来奇怪了
//    @Override
//    protected void initArgs() {
//        XRouter.getInstance().inject(this);
//    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    //这个页面只是一个装载cardlist的容器布局文件，不是cardlist的绘制文件，绘制文件在cardlistAdapter中进行
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_list_view_refresh_container;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter = new CardViewListAdapter());

        //筛选标签页对应的内容
        for (HiresInfos info : hiresInfosList){
            //注意这里值的比较不要使用 == 要使用equals方法
            if (info.getProject_type().equals(tabtitle)){
                toolTabCardInfos.add(info);
            }
        }

        //加载数据集合，数据库获取到的数据，通过适配器的refresh传给adapeter
        mAdapter.refresh(toolTabCardInfos);

        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initListeners() {
        mAdapter.setOnItemClickListener(null);
    }
}
