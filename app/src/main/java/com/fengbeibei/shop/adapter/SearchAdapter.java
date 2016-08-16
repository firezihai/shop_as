package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fengbeibei.shop.bean.Goods;

import java.util.List;

/**
 * SearchAdapter
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-13 9:54
 */
public class SearchAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Goods> mGoodsList;

    public SearchAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mGoodsList == null ? 0 : mGoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
