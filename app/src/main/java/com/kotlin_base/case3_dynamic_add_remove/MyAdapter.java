package com.kotlin_base.case3_dynamic_add_remove;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by asus on 2018/1/3.
 */

public class MyAdapter extends FragmentStatePagerAdapter {

    private List<MyFragment> myFragments;

    public MyAdapter(FragmentManager fm, List<MyFragment> fragments) {
        super(fm);
        myFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);
    }

    @Override
    public int getCount() {
        return myFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }

}
