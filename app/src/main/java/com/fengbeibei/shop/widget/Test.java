package com.fengbeibei.shop.widget;

import android.content.Context;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.interf.ViewInterface;
import com.fengbeibei.shop.utils.NetUtil;

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
        Context context = mSearchNetErrorView.getCurrentContext();
        NetworkInfo networkInfo = NetUtil.getActiveNetwork(context);
        if(!networkInfo.isConnected() && !networkInfo.isAvailable()){
            Toast.makeText(MyApplication.getContext(),"当前网络连接异常，请检查网络后再试吧",Toast.LENGTH_SHORT).show();
        }
        ViewInterface viewInterface = mSearchNetErrorView.getViewInterface();
        viewInterface.a();
    }
}
