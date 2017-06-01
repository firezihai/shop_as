package com.zihai.shop.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zihai.shop.R;

/**
 * SearchBigItemViewHolder
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 10:43
 */
public class SearchBigItemViewHolder extends RecyclerViewHolder{
    public TextView mGoodsName;
    public TextView mGoodsActivity;
    public ImageView mGoodsImage;
    public TextView mGoodsPrice;
    public TextView mPromotion1;
    public TextView mPromotion2;
    public TextView mPromotion3;
    public TextView mShipFree;
    public TextView mGoodsNoSale;
    public SearchBigItemViewHolder(int params, View itemView) {
        super(params, itemView);
        mGoodsImage = (ImageView)itemView.findViewById(R.id.goodsImage);
        mGoodsName = (TextView) itemView.findViewById(R.id.goodsName);
        mGoodsActivity = (TextView) itemView.findViewById(R.id.tv_activity);
        mGoodsPrice = (TextView) itemView.findViewById(R.id.goodsPrice);
        mPromotion1 = (TextView) itemView.findViewById(R.id.tv_promotion_1);
        mPromotion2 = (TextView) itemView.findViewById(R.id.tv_promotion_2);
        mPromotion3 = (TextView) itemView.findViewById(R.id.tv_promotion_3);
        mShipFree = (TextView) itemView.findViewById(R.id.tv_ship_free);
        mGoodsNoSale = (TextView) itemView.findViewById(R.id.tv_goods_no_sale);
    }
}
