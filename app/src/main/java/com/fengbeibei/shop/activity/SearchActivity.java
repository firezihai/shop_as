package com.fengbeibei.shop.activity;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.interf.ViewInterface;
import com.fengbeibei.shop.utils.NetUtil;
import com.fengbeibei.shop.widget.SearchNetErrorView;

import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;

public class SearchActivity extends BaseActivity{
    @BindView(R.id.netErrorView)
    SearchNetErrorView mSearchNetErrorView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init(Bundle savedInstancedState) {
        super.init(savedInstancedState);
    }

    @Override
    public void initData() {
        HttpClientHelper.asynGet(Constants.SEARCH_GOODS_LIST_URL, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void initView() {
        NetworkInfo networkInfo = NetUtil.getActiveNetwork(this);
        if(!networkInfo.isConnected() && !networkInfo.isAvailable()){
            mSearchNetErrorView.setVisibility(View.VISIBLE);
            mSearchNetErrorView.setViewInterface(new ViewInterface() {
                @Override
                public void a() {
                    Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            mSearchNetErrorView.setVisibility(View.GONE);
        }

    }
}
