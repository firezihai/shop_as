package com.zihai.shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.activity.OrderListActivity;
import com.zihai.shop.bean.Order;
import com.zihai.shop.bean.Order.Goods;
import com.zihai.shop.common.AnimateFirstDisplayListener;
import com.zihai.shop.common.IntentHelper;
import com.zihai.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter{
	private ArrayList<Order> mOrderList;
	private OrderListActivity mOrderListActivity;
	private int mLayoutResourceId;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
	private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
	public OrderListAdapter(OrderListActivity orderListActivity,int layoutResourceId) {
		super();
		mOrderListActivity = orderListActivity;
		mLayoutResourceId = layoutResourceId;
	}
	public void setData(ArrayList<Order> data){
        if(mOrderList != null && !data.isEmpty()){
            mOrderList.addAll(data);
        }else {
            mOrderList = data;
        }
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOrderList == null ? 0 : mOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mOrderList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null){
			convertView = LayoutInflater.from(mOrderListActivity).inflate(mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.goodsList = (LinearLayout)convertView.findViewById(R.id.goodsList);
			holder.storeName = (TextView)convertView.findViewById(R.id.tv_store_name);
            holder.shipFee = (TextView) convertView.findViewById(R.id.tv_ship_fee);
			holder.orderAmount = (TextView) convertView.findViewById(R.id.tv_order_amount);
			holder.stateDesc = (TextView) convertView.findViewById(R.id.tv_order_state);
            holder.operateLayout = (LinearLayout) convertView.findViewById(R.id.ll_operate_layout);
            holder.deleteOrderBtn = (TextView) convertView.findViewById(R.id.tv_delete_order);
            holder.cancelOrderBtn = (TextView) convertView.findViewById(R.id.tv_cancel_order);
            holder.orderPayBtn = (TextView) convertView.findViewById(R.id.tv_order_pay);
            holder.orderEvaluateBtn = (TextView) convertView.findViewById(R.id.tv_order_eval);
            holder.orderReceiptBtn = (TextView) convertView.findViewById(R.id.tv_order_receipt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
	//	String str = String.format("共有1件商品 合计￥ %s (含运费￥%s )", order.getOrderAmount(),order.getShippingFee());
        Order order = mOrderList.get(position);
		holder.storeName.setText(order.getStoreName());
		holder.stateDesc.setText(order.getStateDesc());
        holder.orderAmount.setText(order.getOrderAmount());
        holder.shipFee.setText(String.format((String) holder.shipFee.getText(), order.getShippingFee()));
        if(order.getIfCancel()){
            holder.cancelOrderBtn.setVisibility(View.VISIBLE);
            holder.cancelOrderBtn.setEnabled(true);
            holder.cancelOrderBtn.setClickable(true);
        }

        if(order.getIfReceipt()){
            holder.orderReceiptBtn.setVisibility(View.VISIBLE);
            holder.orderReceiptBtn.setEnabled(true);
            holder.orderReceiptBtn.setClickable(true);
        }

        if(order.getOrderState().equals("10")){
            holder.orderPayBtn.setVisibility(View.VISIBLE);
            holder.orderPayBtn.setEnabled(true);
            holder.orderPayBtn.setClickable(true);
        }
		RegOnClickListener regOnClickListener = new RegOnClickListener(order.orderId);
        holder.cancelOrderBtn.setOnClickListener(regOnClickListener);
        holder.orderReceiptBtn.setOnClickListener(regOnClickListener);
        holder.orderPayBtn.setOnClickListener(regOnClickListener);
		holder.goodsList.setOnClickListener(regOnClickListener);
		ArrayList<Goods> goodsList = order.getGoods();
		int size = goodsList.size();
		holder.goodsList.removeAllViews();
		if (size > 0){
			for(Goods goods : goodsList){
				View goodsView = LayoutInflater.from(mOrderListActivity).inflate(R.layout.order_goods_item, null);
				TextView goodsName = (TextView)goodsView.findViewById(R.id.goodsName);
				TextView goodsSpec = (TextView)goodsView.findViewById(R.id.goodsSpec);
				TextView goodsNum = (TextView)goodsView.findViewById(R.id.goodsNum);
				TextView goodsPayPrice = (TextView) goodsView.findViewById(R.id.goodsPayPrice);
				ImageView goodsImage = (ImageView) goodsView.findViewById(R.id.goodsImage);
				goodsName.setText(goods.getGoodsName());
			//	goodsSpec.setText(goods.getGoodsSpec());
				goodsPayPrice.setText("￥"+goods.getGoodsPayPrice());
				goodsNum.setText("x"+goods.getGoodsNum());
				mImageLoader.displayImage(goods.getGoodsImage(), goodsImage, mOptions, mAnimateFirstListener);
				holder.goodsList.addView(goodsView);
			}
		}
		return convertView;
	}
	class ViewHolder{
		TextView storeName;
		LinearLayout goodsList;
		TextView orderAmount;
		TextView stateDesc;
        LinearLayout operateLayout;
        TextView shipFee;
        TextView deleteOrderBtn;
        TextView cancelOrderBtn;
        TextView orderEvaluateBtn;
        TextView orderPayBtn;
        TextView orderReceiptBtn;
	}

    class RegOnClickListener implements View.OnClickListener{
        private String orderId;

        public RegOnClickListener(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel_order:
                    OrderListActivity.cancelOrder(mOrderListActivity,orderId);
                    break;
				case R.id.tv_order_pay:

					break;
				case R.id.tv_order_receipt:

					break;
				case R.id.goodsList:
					IntentHelper.orderDetail(mOrderListActivity,orderId);
					break;
            }
        }
    }
}
