package com.fengbeibei.shop.callback;

import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.interf.SearchHeaderInterface;

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
    public void onFilter() {

    }

    @Override
    public void onSearch() {
      SearchResultActivity.onSearch(mSearchResultActivity);

    }


}
