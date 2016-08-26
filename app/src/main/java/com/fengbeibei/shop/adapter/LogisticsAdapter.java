package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Logistics;

import java.util.List;

/**
 * LogisticsAdapter
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 15:19
 */
public class LogisticsAdapter extends BaseAdapter{
    private List<Logistics.DeliverInfoBean> mDeliverInfoBeans;
    private Context mContext;

    public LogisticsAdapter(List<Logistics.DeliverInfoBean> deliverInfoBeans, Context context) {
        mDeliverInfoBeans = deliverInfoBeans;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDeliverInfoBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mDeliverInfoBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_logistics_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mTopLine = (TextView) convertView.findViewById(R.id.tv_top_line);
            viewHolder.mDeliverIcon = (ImageView)convertView.findViewById(R.id.iv_deliver_icon);
            viewHolder.mDeliverInfo = (TextView) convertView.findViewById(R.id.tv_deliver_info);
            viewHolder.mDeliverDate = (TextView) convertView.findViewById(R.id.tv_deliver_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Logistics.DeliverInfoBean deliverInfoBean = mDeliverInfoBeans.get(position);
        if( position == 0){
            viewHolder.mTopLine.setBackgroundResource(R.color.white);
            viewHolder.mDeliverIcon.setImageResource(R.drawable.icon_current_location);
            viewHolder.mDeliverInfo.setTextColor(ContextCompat.getColor(mContext,R.color.green));
            viewHolder.mDeliverInfo.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }
        viewHolder.mDeliverInfo.setText(deliverInfoBean.getContext());
        viewHolder.mDeliverDate.setText(deliverInfoBean.getTime());
        return convertView;
    }

    class ViewHolder{
        ImageView mDeliverIcon;
        TextView mDeliverInfo;
        TextView mDeliverDate;
        TextView mTopLine;
    }
}
