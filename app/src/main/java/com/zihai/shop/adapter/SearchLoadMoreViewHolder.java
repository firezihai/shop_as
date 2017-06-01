package com.zihai.shop.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihai.shop.R;

/**
 * SearchLoadMoreViewHolder
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-17 14:12
 */
public class SearchLoadMoreViewHolder extends RecyclerViewHolder{
    public LinearLayout mProgressBarLayout;
    public TextView mNoMore;
    public TextView mLoadError;
    public SearchLoadMoreViewHolder(int ViewType, View itemView) {
        super(ViewType, itemView);
        mProgressBarLayout = (LinearLayout) itemView.findViewById(R.id.ll_search_loading_more);
        mNoMore = (TextView) itemView.findViewById(R.id.tv_search_no_more);
        mLoadError = (TextView) itemView.findViewById(R.id.tv_search_load_error);
    }
}
