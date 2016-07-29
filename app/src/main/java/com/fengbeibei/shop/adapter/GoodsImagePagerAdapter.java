package com.fengbeibei.shop.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/28.
 */
public class GoodsImagePagerAdapter extends PagerAdapter{
    private ArrayList<ImageView> mData;


    /**
     * @param data
     */
    public GoodsImagePagerAdapter(ArrayList<ImageView> data) {
        super();
        this.mData = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.mData.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewGroup) container).removeView((View) object);
        object=null;
        //   super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        ((ViewPager) container).addView(mData.get(position));
        return mData.get(position);
    }
}
