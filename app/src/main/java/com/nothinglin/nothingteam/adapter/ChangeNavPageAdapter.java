package com.nothinglin.nothingteam.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by jpeng on 16-11-14.
 */
public class ChangeNavPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public ChangeNavPageAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
