package com.nothinglin.nothingteam.fragment;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.bean.HiresInfosTabs;
import com.nothinglin.nothingteam.dao.HiresInfosDao;
import com.nothinglin.nothingteam.fragment.homepages.ToolTabCardListFragment;
import com.nothinglin.nothingteam.utils.GlobalThreadPool;
import com.nothinglin.nothingteam.widget.DemoDataProvider;
import com.nothinglin.nothingteam.widget.StickyNavigationLayout;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements BaseBanner.OnItemClickListener<BannerItem> {

    //注册视图组件
    @BindView(R.id.titlebar)
    TitleBar titlebar;//标题栏
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;//home页面的标签切换nav
    @BindView(R.id.view_pager)
    ViewPager viewPager;//主要显示内容
    @BindView(R.id.sticky_navigation_layout)
    StickyNavigationLayout stickyNavigationLayout;//整体界面布局框架
    //注册轮播图组件
    @BindView(R.id.top_view)
    SimpleImageBanner sib_corner_rectangle;

    //获取tooltab（选项卡）主题标签的标题的容器
    private List<String> titles = new ArrayList<>();
    //hiresInfosList装满了全部的募招内容，获取数据库中招募信息的全部数据
    private List<HiresInfos> hiresInfosList = new ArrayList<>();
    //定义募招信息的数据库操作类
    HiresInfosDao hiresInfosDao = new HiresInfosDao();
    //handler是线程信息传递的重要工具，用来接收子线程中的数据
    public Handler handler;

    private List<HiresInfosTabs> hiresInfosTabsList = new ArrayList<>();


    //mPictures是用来存放轮播图图片的
    private List<BannerItem> mPictures;

    //获取layout布局资源
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    //重写界面标题栏
    @Override
    protected com.xuexiang.xui.widget.actionbar.TitleBar initTitle() {
        //设置标题栏的标题
        titlebar.setTitle("这里是HomePage");
        //标题栏沉浸式，没有的话标题栏会遮挡不住轮播图
        titlebar.setImmersive(true);
        //设置标题栏左部的部件是否显示
        titlebar.setLeftVisible(true);
        //初始化标题栏
        initTitleBar(0);
        return null;
    }

    //初始化标题栏
    private void initTitleBar(float moveRatio) {
        /**
         * 这一部分是标题栏，一开始显示的只是轮播图，随着轮播图的下拉就把标题栏给显示出来
         */
        //颜色渐变值类，下拉过程的颜色渐变
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        //标题栏上的文字、图标在渐变的过程中的颜色变化，只显示轮播图时图标和文字为白色，下拉显示了标题栏后，图标和文字的颜色 变成黑色
        int backColor = (int) argbEvaluator.evaluate(moveRatio, Color.WHITE, Color.BLACK);
        //设置标题栏图标资源
        Drawable wrapDrawable = DrawableCompat.wrap(ResUtils.getDrawable(getContext(), R.drawable.ningmeng));
        DrawableCompat.setTint(wrapDrawable, backColor);
        int bgColor = (int) argbEvaluator.evaluate(moveRatio, Color.TRANSPARENT, ThemeUtils.resolveColor(getContext(), com.xuexiang.xui.R.attr.xui_actionbar_color));

        //设置标题栏的左部资源图标
        titlebar.setLeftImageDrawable(wrapDrawable);
        //设置标题栏的背景色
        titlebar.setBackgroundColor(bgColor);
        //设置标题栏中间标题的默认状态，也就是隐藏
        titlebar.getCenterText().setAlpha(moveRatio);

    }

    @Override
    protected void initViews() {

        //初始化fragment适配器
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //当tablayout数量多到超过界面时可以左右滑动来展示更多的标签 -- MODE_SCROLLABLE
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //获取选项卡的标签title，过滤重复的数据
        String[] tabtitles = getTabTitles();

        //遍历标签页的数量，设置标签页的名字并且给每个标签也添加简单的list帧布局，也意味着每个标签都有自己的fragment，而他们的fragment对应他们的title
        for (String title : tabtitles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
            //把获取到的数据直接传入ToolTabCardListFragment，不用再开启子线程来重新获取数据了
            adapter.addFragment(new ToolTabCardListFragment(title,hiresInfosList), title);//注意这里给ToolTabCardListFragment传递了title参数，用户匹配数据库的对应页面对应的数据
        }

        //设置预加载界面数量
        viewPager.setOffscreenPageLimit(titles.size() - 1);
        //将适配器处理的内容显示到viewpager中
        viewPager.setAdapter(adapter);
        //将view和tablayout相关联
        tabLayout.setupWithViewPager(viewPager);
        //将tablayout的字体设置为XUI的字体
        ViewUtils.setViewsFont(tabLayout);

        //对标题栏的滚动进行监听，滚动后标签栏仍然显示，标题栏则消失
        stickyNavigationLayout.setOnScrollChangeListener(this::initTitleBar);

        //注册轮播图视图
        mPictures = DemoDataProvider.getBannerList();
        sib_corner_rectangle();

        //设置沉浸式状态栏
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


    //获取选项卡的标签title，过滤重复的数据
    @SuppressLint("HandlerLeak")
    private String[] getTabTitles() {

        //开启一个子线程thread，获取数据库中的hiresinfos，全部数据！
        GlobalThreadPool.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //调用数据库操作类方法
                hiresInfosList = hiresInfosDao.getHiresInfoAll();
                hiresInfosTabsList = hiresInfosDao.getHiresInfoTabsAll();

                //将标签送入信息列表中，这样后面ToolTabCardListFragment --> CardViewListAdapter 循环传值的时候才方便
                for (HiresInfos info : hiresInfosList){
                    info.setTabs(hiresInfosTabsList);
                }

                //通过message方法把联网获取到的MySQL中的数据从子线程传递到主线程中去
                Message message = new Message();
                message.obj = hiresInfosList;
                //主线程通过handler来响应和接收子线程传来的数据
                handler.sendMessage(message);
            }
        });

        //上面handler发送了信息，这里需要立刻接收，并且赋值给全局变量
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                hiresInfosList = (List<HiresInfos>) msg.obj;
            }
        };

        /**
         * 上面的数据过程是子线程，由于下面立刻就要用到了hiresInfosList，但是开启子线程之后就分两条路来走了
         * 子线程和主线程不同步，主线程要获取hiresInfosList，但是子线程没有从数据库中拿到且返回给主线程
         * 这样主线程由于跑得快，就没有拿到数据，报错就报空指针，应该让主线程等一等子线程，等子线程获取数据后再
         * 获取子线程的数据处理下一步，初步使用thread.sleep方法让主线程睡眠
         */

        try {
            // thread --> handler --> thread.sleep
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //获取数据库中选项卡的标题，标题设立在招聘infos的bean中
        //使用项目类型作为标签标题
        for (HiresInfos title : hiresInfosList) {
            titles.add(title.getProject_type());
        }

        //利用hashset过滤掉重复的数据，因为众多信息中，有属于比赛项目的item有很多，（1，1，2，2，3，3，）-->（1，2，3）
        HashSet hashSet = new HashSet(titles);
        titles.clear();//清空原本的titles集合中的数据
        titles.addAll(hashSet);//把过滤重复数据后的数据集全部添加到titles集合中

        String[] tabtitles = new String[titles.size()];//初始化一个数组
        //将list转化为String[]数组
        tabtitles = titles.toArray(new String[titles.size()]);

        return tabtitles;

    }


    //注册轮播图视图
    private void sib_corner_rectangle() {
        sib_corner_rectangle.setSource(mPictures).setOnItemClickListener(this).startScroll();
    }

    @Override
    public void onItemClick(View view, BannerItem item, int position) {
        //点击轮播图中的每一项时点击事件
        Toast.makeText(getContext(), "中间点击" + position + ", item:" + item.title, Toast.LENGTH_SHORT).show();
    }
}
