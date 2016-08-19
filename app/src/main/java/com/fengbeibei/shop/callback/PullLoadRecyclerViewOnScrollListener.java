package com.fengbeibei.shop.callback;

import android.annotation.TargetApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.interf.PullLoadRecyclerViewInterface;

/**
 * PullLoadRecyclerViewOnScrollListener
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-17 15:10
 */
public class PullLoadRecyclerViewOnScrollListener implements PullLoadRecyclerViewInterface{
    private SearchResultActivity mSearchResultActivity;

    public PullLoadRecyclerViewOnScrollListener(SearchResultActivity searchResultActivity) {
        mSearchResultActivity = searchResultActivity;
    }
    @TargetApi(12)
    @Override
    public void hideSearchTitle() {
        SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchTitle.animate().translationY(-SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchTab.getHeight()).setInterpolator(new DecelerateInterpolator(2.0f));


    }

    @Override
    public void pullLoadData(RecyclerView recyclerView, int dx, int dy) {
        SearchResultActivity.updateData(mSearchResultActivity,recyclerView.getScrollState());
    }

    @Override
    public void showPage(RecyclerView recyclerView,int newState) {
        if(newState == RecyclerView.SCROLL_STATE_IDLE){
            SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchPageView.setVisibility(View.GONE);
            return ;
        }
        SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchPageView.setVisibility(View.VISIBLE);
    }
    @TargetApi(12)
    @Override
    public void showSearchTitle() {
        SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchTitle.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(2.0f));
    }
}
