package com.fengbeibei.shop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;

/**
 * SearchSmallItemViewHolder
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 11:24
 */
public class SearchSmallItemViewHolder extends RecyclerViewHolder{
    public TextView mGoodsName;
    public ImageView mGoodsImage;
    public TextView mGoodsPrice;
    public TextView mEvalCount;
    public TextView mGoodsActivity;
    public TextView mStoreName;
    public TextView mPromotion1;
    public TextView mPromotion2;
    public TextView mPromotion3;
    public TextView mShipFree;
    public TextView mGoodsNoSale;
    public SearchSmallItemViewHolder(int ViewType, View itemView) {
        super(ViewType, itemView);
        mGoodsName = (TextView)itemView.findViewById(R.id.goodsName);
        mGoodsPrice = (TextView) itemView.findViewById(R.id.goodsPrice);
        mGoodsImage = (ImageView) itemView.findViewById(R.id.goodsImage);
        mGoodsActivity = (TextView) itemView.findViewById(R.id.tv_activity);
        mEvalCount = (TextView) itemView.findViewById(R.id.tv_eval_count);
        mStoreName = (TextView) itemView.findViewById(R.id.tv_store_name);
        mPromotion1 = (TextView) itemView.findViewById(R.id.tv_promotion_1);
        mPromotion2 = (TextView) itemView.findViewById(R.id.tv_promotion_2);
        mPromotion3 = (TextView) itemView.findViewById(R.id.tv_promotion_3);
        mShipFree = (TextView) itemView.findViewById(R.id.tv_ship_free);
        mGoodsNoSale = (TextView) itemView.findViewById(R.id.tv_goods_no_sale);
    }
}
