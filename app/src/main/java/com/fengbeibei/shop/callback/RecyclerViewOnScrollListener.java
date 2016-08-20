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
        int max2 = position[0];
        int max = findMax(position);
        int length  = position.length;
        int[] firstVisible = layoutManager.findFirstVisibleItemPositions(new int[spanCount]);
        int first = firstVisible[0];
        Log.i("RecyclerOnScroll", "childCount=" + childCount + "----itemCount=" + itemCount + "---spanCount=" + spanCount + "---dy=" + dy+"--max="+max+"--first="+first+"--max2="+max2);
        RecyclerViewAdapter adapter = mPullLoadRecyclerView.getAdapter();
        if(dy > 0){
            mPullLoadRecyclerView.mOnScrollCallback.hideSearchTitle();
        }else{
            mPullLoadRecyclerView.mOnScrollCallback.showSearchTitle();
        }
        if(max2 + spanCount == itemCount){
            Log.i("RecyclerOnScroll", "max + spanCount" + itemCount);
            adapter.setScrollEnd(true);
        }
        if(childCount > 0 && max== itemCount -1 && dy >0){

       //     mPullLoadRecyclerView.mOnScrollCallback.pullLoadData(recyclerView, dx, dy);
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
