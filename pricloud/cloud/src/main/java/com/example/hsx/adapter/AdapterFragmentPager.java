package com.example.hsx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hsx on 17-8-30.
 */

public class AdapterFragmentPager extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = null;
    public AdapterFragmentPager(FragmentManager fm, List<Fragment> l) {
        super(fm);
        this.mFragmentList = l;
    }

    @Override
    public Fragment getItem(int position) {
//        HanLog.write("OWNCLOUD", " AdapterFragmentPager getItem()");
        return this.mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }
}
