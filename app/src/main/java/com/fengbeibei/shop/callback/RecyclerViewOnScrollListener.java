package com.fengbeibei.shop.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fengbeibei.shop.widget.PullLoadRecyclerView;

/**
 * RecyclerViewOnScrollListener
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 10:18
 */
public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener{
    private PullLoadRecyclerView mPullLoadRecyclerView;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      //  super.onScrolled(recyclerView, dx, dy);

        final  int oldY = 0;
        if(dy > oldY ){
            mPullLoadRecyclerView.mOnScrollCallback.hideSearchTitle();
        }else{
            mPullLoadRecyclerView.mOnScrollCallback.showSearchTitle();
        }
    }

    public RecyclerViewOnScrollListener(PullLoadRecyclerView pullLoadRecyclerView) {
        super();
        mPullLoadRecyclerView = pullLoadRecyclerView;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //super.onScrollStateChanged(recyclerView, newState);
      //  mPullLoadRecyclerView.mOnScrollCallback.showSearchTitle();
        mPullLoadRecyclerView.mOnScrollCallback.showPage(recyclerView,newState);
    }
}
