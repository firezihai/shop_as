package com.zihai.shop.interf;

import android.support.v7.widget.RecyclerView;

/**
 * PullLoadRecyclerViewInterface
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-17 15:02
 */
public abstract  interface PullLoadRecyclerViewInterface {
    public abstract void showPage(RecyclerView recyclerView,int newState);
    public abstract void pullLoadData(RecyclerView recyclerView,int dx,int dy);
    public abstract void showSearchTitle();
    public abstract void hideSearchTitle();
}
