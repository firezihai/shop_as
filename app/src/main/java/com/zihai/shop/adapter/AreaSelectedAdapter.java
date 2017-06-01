package com.zihai.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.interf.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * AreaSelectedAdapter
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 16:41
 */
public class AreaSelectedAdapter extends BaseAdapter{
    private List<Area> mData = new ArrayList<Area>();
    private Context mContext;

    public AreaSelectedAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Area> data){
        mData.clear();
        if(data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return getArea(position);
    }

    public Area getArea(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mAreaName;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_selected_area,null);
            mAreaName= (TextView)convertView.findViewById(R.id.tv_area_name);
            convertView.setTag(mAreaName);
        }else{
            mAreaName = (TextView)convertView.getTag();
        }
        mAreaName.setText(mData.get(position).getName());
        return convertView;
    }


}
