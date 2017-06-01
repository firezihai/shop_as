package com.zihai.shop.callback;

import android.widget.ImageView;

import com.zihai.shop.R;
import com.zihai.shop.activity.SearchResultActivity;
import com.zihai.shop.interf.SearchHeaderInterface;
import com.zihai.shop.widget.PullLoadRecyclerView;

/**
 * SearchHeaderListener
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-10 14:51
 */
public class SearchHeaderListener implements SearchHeaderInterface {
    private SearchResultActivity mSearchResultActivity;

    public SearchHeaderListener(SearchResultActivity searchResultActivity) {
        mSearchResultActivity = searchResultActivity;
    }

    @Override
    public void onBack() {
        mSearchResultActivity.finish();
    }

    @Override
    public void onSwitchView(ImageView imageView) {
       // RecyclerView.LayoutManager layoutManager =  SearchResultActivity.getViewHolder(mSearchResultActivity).mPullLoadRecyclerView.getLayoutManager();
       int spanCount =SearchResultActivity.getViewHolder(mSearchResultActivity).mPullLoadRecyclerView.getSpanCount();
        if(spanCount == PullLoadRecyclerView.TYPE_LIST){
            SearchResultActivity.getViewHolder(mSearchResultActivity).mPullLoadRecyclerView.setSpanCount(PullLoadRecyclerView.TYPE_GRID);
            imageView.setImageResource(R.drawable.cate_big);
        }else{
            SearchResultActivity.getViewHolder(mSearchResultActivity).mPullLoadRecyclerView.setSpanCount(PullLoadRecyclerView.TYPE_LIST);
            imageView.setImageResource(R.drawable.cate_list);
        }
    }

    @Override
    public void onSearch() {
      SearchResultActivity.onSearch(mSearchResultActivity);

    }


}
