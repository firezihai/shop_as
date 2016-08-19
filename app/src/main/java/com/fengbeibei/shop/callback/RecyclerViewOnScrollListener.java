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
        int max = position[0];
        int[] firstVisible = layoutManager.findFirstVisibleItemPositions(new int[spanCount]);
        int first = firstVisible[0];
        RecyclerViewAdapter adapter = mPullLoadRecyclerView.getAdapter();
       // SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchTab.getHeight()
        if(dy > 0){
            mPullLoadRecyclerView.mOnScrollCallback.hideSearchTitle();
        }else{
            mPullLoadRecyclerView.mOnScrollCallback.showSearchTitle();
        }
        if((max + spanCount) == itemCount) {

            adapter.setScrollEnd(true);
            mPullLoadRecyclerView.mOnScrollCallback.pullLoadData(recyclerView,dx,dy);
        }
       /* if(spanCount == 1){
               int[] visibleItem = mPullLoadRecyclerView.getSmallVisibleItem();
                if(visibleItem.length == 0){
                    int[] position = new int[spanCount];
                    mPullLoadRecyclerView.setBigVisibleItem(position);
                }else{
                    int[] bigVisibleItem = mPullLoadRecyclerView.getBigVisibleItem();
                    int[] position = layoutManager.findLastVisibleItemPositions(bigVisibleItem);
                    mPullLoadRecyclerView.setBigVisibleItem(position);
                }
        }else{

        }*/

        Log.i("RecyclerOnScroll", "childCount=" + childCount + "----itemCount=" + itemCount + "---spanCount=" + spanCount + "---dy=" + dy+"--max="+max+"--first="+first);
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
