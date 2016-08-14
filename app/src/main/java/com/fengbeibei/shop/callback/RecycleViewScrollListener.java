package com.fengbeibei.shop.callback;

import android.support.v7.widget.RecyclerView;

import com.fengbeibei.shop.widget.PullLoadRecycleView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-14 21:07
 */
public class RecycleViewScrollListener extends RecyclerView.OnScrollListener{
    private PullLoadRecycleView mPullLoadRecycleView;

    public RecycleViewScrollListener(PullLoadRecycleView pullLoadRecycleView) {
        mPullLoadRecycleView = pullLoadRecycleView;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
