package com.fengbeibei.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GoodsEvaluateFragmentPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> mFragments;
    public void setData(ArrayList<Fragment> fragments){
        mFragments = fragments;
    }
    public GoodsEvaluateFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    public interface OnReloadListener
    {
        public void onReload();
    }
}
