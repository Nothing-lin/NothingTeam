package com.nothinglin.nothingteam.fragment.carddetail;

import com.nothinglin.nothingteam.R;
import com.nothinglin.nothingteam.base.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

@Page
public class ProjectDetailFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_detail_project_detail;
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {

    }
}
