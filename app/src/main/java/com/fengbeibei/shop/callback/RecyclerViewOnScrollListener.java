package com.fengbeibei.shop.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.adapter.RecyclerViewAdapter;
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
       super.onScrolled(recyclerView, dx, dy);
        StaggeredGridLayoutManager layoutManager= mPullLoadRecyclerView.getLayoutManager();
        int childCount = layoutManager.getChildCount();

        int itemCount = layoutManager.getItemCount();
        int spanCount = layoutManager.getSpanCount();
        int[] position =layoutManager.findLastVisibleItemPositions(new int[spanCount]);
        int max = findMax(position);
        if(dy > 0 && max>6 ){
            mPullLoadRecyclerView.mOnScrollCallback.hideSearchTitle();
        }else{
            mPullLoadRecyclerView.mOnScrollCallback.showSearchTitle();
        }
        if(childCount > 0 && max== itemCount -1 && dy >0){

           mPullLoadRecyclerView.mOnScrollCallback.pullLoadData(recyclerView, dx, dy);
        }
    }

    public RecyclerViewOnScrollListener(PullLoadRecyclerView pullLoadRecyclerView) {
        super();
        mPullLoadRecyclerView = pullLoadRecyclerView;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        mPullLoadRecyclerView.mOnScrollCallback.showPage(recyclerView,newState);
    }

    public int findMax(int[] lastPosition){
        int max = lastPosition[0];
        for(int value : lastPosition ){
            if(value > max){
                max = value;
            }
        }
        return max;
    }
}
