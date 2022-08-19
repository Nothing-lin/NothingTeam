package com.nothinglin.nothingteam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.adapter.ChangeNavPageAdapter;
import com.nothinglin.nothingteam.base.BaseActivity;
import com.nothinglin.nothingteam.fragment.HomeFragment;
import com.nothinglin.nothingteam.fragment.MeFragment;
import com.nothinglin.nothingteam.fragment.MessageFragment;
import com.nothinglin.nothingteam.fragment.TeamFragment;
import com.nothinglin.nothingteam.utils.XToastUtils;
import com.nothinglin.nothingteam.widget.StatusBarUtil;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener, BadgeDismissListener {

    //注册底部菜单栏视图组件==findviewbyid
    @BindView(R.id.tabbar)
    JPTabBar mTabbar;

    private int[] mTitles = {R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4};
    private int[] mSelectIcons = {R.drawable.nav_01_pre, R.drawable.nav_02_pre, R.drawable.nav_04_pre, R.drawable.nav_05_pre};
    private int[] mNormalIcons = {R.drawable.nav_01_nor, R.drawable.nav_02_nor, R.drawable.nav_04_nor, R.drawable.nav_05_nor};

    //底部菜单栏fragment切换
    private List<Fragment> list = new ArrayList<>();
    @BindView(R.id.view_pager)
    ViewPager mPager;

    //注册底部菜单栏的每项fragment界面
    HomeFragment homeFragment;
    MeFragment meFragment;
    MessageFragment messageFragment;
    TeamFragment teamFragment;


    //setContentView(R.layout.activity_main);指向需要展示的界面的布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        //给底部导航栏置入文字的图标
        mTabbar.setTitles(mTitles);
        mTabbar.setNormalIcons(mNormalIcons);
        mTabbar.setSelectedIcons(mSelectIcons);
        mTabbar.generate();
        //页面可以滑动
        mTabbar.setGradientEnable(true);
        mTabbar.setPageAnimateEnable(true);
        mTabbar.setTabTypeFace(XUI.getDefaultTypeface());
        mTabbar.setTabListener(this);

        //实例化fragment界面
        homeFragment = new HomeFragment();
        meFragment = new MeFragment();
        messageFragment = new MessageFragment();
        teamFragment = new TeamFragment();

        list.add(homeFragment);
        list.add(teamFragment);
        list.add(messageFragment);
        list.add(meFragment);

        //设置页面适配
        mPager.setAdapter(new ChangeNavPageAdapter(getSupportFragmentManager(), list));
        mTabbar.setContainer(mPager);

        //设置Badge（徽章）消失的代理，刷新icon的作用吧
        mTabbar.setDismissListener(this);
        mTabbar.setTabListener(this);

        //底部导航栏中间按钮的触发事件
        if (mTabbar.getMiddleView() != null) {
            mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, "中间点击", Toast.LENGTH_SHORT).show();
                    showBottomSheetGrid();
                }
            });
        }


        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    private void showBottomSheetGrid(){
        //表格排列的顺序
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;

        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.drawable.icon_more_operation_share_friend,"分享到微信",TAG_SHARE_WECHAT_FRIEND,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_moment,"分享到朋友圈",TAG_SHARE_WECHAT_MOMENT,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_weibo,"分享到微博",TAG_SHARE_WEIBO,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_chat,"分享到私信",TAG_SHARE_CHAT,BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_save,"保存到本地",TAG_SHARE_LOCAL,BottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener(((dialog, itemView) -> {
                    //点击之后退出底部弹窗
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    XToastUtils.toast("tag:" + tag + ", content:" + itemView.toString());
                })).build().show();


    }


    //实现OnTabSelectListener的接口方法
    @Override
    public void onTabSelect(int index) {
        Toast.makeText(MainActivity.this, "choose the tab index is " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onInterruptSelect(int index) {
        //  if(index==2){
        //   如果这里有需要阻止Tab被选中的话,可以return true
        //      return true;
        //  }
        return false;
    }

    //BadgeDismissListener的接口实现
    @Override
    public void onDismiss(int position) {

    }
}