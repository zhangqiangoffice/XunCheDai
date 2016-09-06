package com.minsheng.app.xunchedai.loan.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 无名小强 on 2016/6/25.
 */
public class BasicFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list = null;

    public BasicFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BasicFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
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

