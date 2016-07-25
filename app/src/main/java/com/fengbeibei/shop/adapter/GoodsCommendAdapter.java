package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.GoodsCommend;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/25.
 */
public class GoodsCommendAdapter extends BaseAdapter{
    private ArrayList<GoodsCommend> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();

    public GoodsCommendAdapter(ArrayList<GoodsCommend> data,Context context) {
        mData = data;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.goods_commend_list_adapter,null);
            viewHolder = new ViewHolder();
            viewHolder.goodsImage = (ImageView) convertView.findViewById(R.id.goodsImage);
            viewHolder.goodsName = (TextView) convertView.findViewById(R.id.goodsName);
            viewHolder.goodsPrice = (TextView) convertView.findViewById(R.id.goodsPrice);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodsCommend goodsCommend = mData.get(position);
        viewHolder.goodsName.setText(goodsCommend.getGoodsName());
        viewHolder.goodsPrice.setText(goodsCommend.getGoodsPrice());
        mImageLoader.displayImage(goodsCommend.getGoodsImageUrl(),viewHolder.goodsImage,mOptions,mAnimateFirstListener);
        setOnClick(convertView,"goods",goodsCommend.getGoodsId());
        return null;
    }

    class ViewHolder{
        ImageView goodsImage;
        TextView goodsName;
        TextView goodsPrice;
    }
    private void setOnClick(View view,final String type,final String data){
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                IntentHelper.filter(mContext, type, data);
            }

        });
    }
}
