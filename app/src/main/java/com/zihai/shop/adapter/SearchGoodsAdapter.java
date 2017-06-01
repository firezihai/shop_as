package com.zihai.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zihai.shop.bean.Goods;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-14 18:28
 */
public class SearchGoodsAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Goods> mGoodsList;
    public SearchGoodsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    public void setData(List<Goods> goodsList){
        if(mGoodsList != null && !goodsList.isEmpty()) {
            mGoodsList.addAll(goodsList);
        }else{
            mGoodsList = goodsList;
        }
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

    class ViewHolder{
        TextView goodsName;
        ImageView goodsImage;
        TextView goodsPrice;
    }
}
