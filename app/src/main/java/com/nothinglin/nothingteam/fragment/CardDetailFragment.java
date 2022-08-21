package com.nothinglin.nothingteam.fragment;

import androidx.viewpager.widget.ViewPager;

import com.donkingliang.labels.LabelsView;
import com.google.android.material.tabs.TabLayout;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.nothinglin.nothingteam.fragment.carddetail.CardDetailCommentFragment;
import com.nothinglin.nothingteam.fragment.carddetail.ProjectDetailFragment;
import com.nothinglin.nothingteam.fragment.messagepages.ChatToTeamFragment;
import com.nothinglin.nothingteam.fragment.messagepages.SystemMessageFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.ArrayList;

import butterknife.BindView;

@Page(name = "项目详情页")
public class CardDetailFragment extends BaseFragment {

    @BindView(R.id.labels)
    LabelsView labelsView;

    //选项标签页面
    @BindView(R.id.tabSegment)
    TabSegment tabSegment;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_detail;
    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        //setImmersive是状态栏的设置，因为一开始已经取消了状态栏的样式了
        super.initTitle().setLeftVisible(true);
        return null;
    }

    @Override
    protected void initViews() {

        setTeamLabel();

        //初始化fragment适配器
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        //将tabLayout设置为固定模式
        tabSegment.setMode(TabSegment.MODE_FIXED);

        //注册适配“系统通告”界面
        tabSegment.addTab(new TabSegment.Tab("项目详情"));
        adapter.addFragment(new ProjectDetailFragment(),"项目详情");

        //注册适配“联系团队”界面
        tabSegment.addTab(new TabSegment.Tab("留言"));
        adapter.addFragment(new CardDetailCommentFragment(),"留言");

        //设置消息页面的视图极限为2个
        mViewPager.setOffscreenPageLimit(2);
        //将视图和tablayout进行绑定
        mViewPager.setAdapter(adapter);
        tabSegment.setupWithViewPager(mViewPager);

    }

    private void setTeamLabel() {
        ArrayList<String> tablist = new ArrayList<>();
        tablist.add("Android");
        tablist.add("IOS");
        tablist.add("前端");
        tablist.add("后台");
        tablist.add("微信开发");
        tablist.add("游戏开发");
        tablist.add("JavaScript");
        tablist.add("C++");
        tablist.add("Java");
        tablist.add("PHP");
        tablist.add("Python");
        tablist.add("Swift");

        labelsView.setLabels(tablist);
        labelsView.setSelectType(LabelsView.SelectType.NONE);
    }
}
