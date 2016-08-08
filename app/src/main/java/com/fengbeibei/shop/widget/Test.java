package com.fengbeibei.shop.widget;

import android.net.NetworkInfo;
import android.view.View;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 21:02
 */
public class Test implements View.OnClickListener{
    private SearchNetErrorView mSearchNetErrorView;
    public Test(SearchNetErrorView searchNetErrorView) {
        mSearchNetErrorView = searchNetErrorView;
    }

    @Override
    public void onClick(View v) {
        NetworkInfo networkInfo = new NetworkInfo();

    }
}
