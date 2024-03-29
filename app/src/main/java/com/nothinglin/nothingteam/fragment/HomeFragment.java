package com.nothinglin.nothingteam.fragment;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.activity.MainActivity;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.bean.HiresInfos;
import com.nothinglin.nothingteam.fragment.homepages.ToolTabCardListFragment;
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
import cn.jpush.im.android.api.JMessageClient;

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

    public HomeFragment(List<HiresInfos> hiresInfosList) {
        this.hiresInfosList = hiresInfosList;
    }

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
        titlebar.setTitle("首页");
        //标题栏沉浸式，没有的话标题栏会遮挡不住轮播图
        titlebar.setImmersive(true);
        //设置标题栏左部的部件是否显示
        titlebar.setLeftVisible(true);
        //初始化标题栏
        initTitleBar(0);
        titlebar.setLeftText("广州软件学院");
        titlebar.setLeftTextSize(35);

        //首页左按钮设置点击事件监听
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "事件触发成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }

        });

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
        int backColor = (int) argbEvaluator.evaluate(moveRatio, Color.WHITE,Color.WHITE);
        //设置标题栏图标资源
        Drawable wrapDrawable = DrawableCompat.wrap(ResUtils.getDrawable(getContext(), R.drawable.position));
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

        //添加全部内容的标签
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        adapter.addFragment(new ToolTabCardListFragment("全部", hiresInfosList), "全部");

        //遍历标签页的数量，设置标签页的名字并且给每个标签也添加简单的list帧布局，也意味着每个标签都有自己的fragment，而他们的fragment对应他们的title
        for (String title : tabtitles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
            //把获取到的数据直接传入ToolTabCardListFragment，不用再开启子线程来重新获取数据了
            adapter.addFragment(new ToolTabCardListFragment(title, hiresInfosList), title);//注意这里给ToolTabCardListFragment传递了title参数，用户匹配数据库的对应页面对应的数据
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
