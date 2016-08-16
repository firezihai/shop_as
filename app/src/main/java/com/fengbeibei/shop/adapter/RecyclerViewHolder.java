package com.fengbeibei.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerViewHolder
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 10:32
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    private int mViewType;
    public RecyclerViewHolder(int ViewType,View itemView) {
        super(itemView);
        mViewType = mViewType;
    }
}
