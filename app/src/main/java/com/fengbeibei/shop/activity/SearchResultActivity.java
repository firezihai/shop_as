package com.fengbeibei.shop.activity;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.callback.SearchHeaderListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.interf.SearchHeaderInterface;
import com.fengbeibei.shop.interf.SearchNetErrorListener;
import com.fengbeibei.shop.utils.NetUtil;
import com.fengbeibei.shop.widget.SearchHeaderView;
import com.fengbeibei.shop.widget.SearchNetErrorView;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;

public class SearchResultActivity extends BaseActivity{
    @BindView(R.id.netErrorView)
    SearchNetErrorView mSearchNetErrorView;
    @BindView(R.id.searchHeader)
    SearchHeaderView mSearchHeaderView;

    private String cateId;
    private String searchKeyword;
    private SearchHeaderListener mSearchHeaderListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void init(Bundle savedInstancedState) {
        super.init(savedInstancedState);

    }

    @Override
    public void initData() {
        String url = Constants.SEARCH_GOODS_LIST_URL;
        if(cateId != null && cateId.equals("")){
            url = url + "&gc_id="+cateId;
        }
        if(searchKeyword != null && searchKeyword.equals("")){
            url = url + "&keyword="+searchKeyword;
        }
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
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
        cateId = getIntent().getStringExtra("gcId");
        NetworkInfo networkInfo = NetUtil.getActiveNetwork(this);
        if(!networkInfo.isConnected() && !networkInfo.isAvailable()){
            mSearchNetErrorView.setVisibility(View.VISIBLE);
            mSearchNetErrorView.setSearchNetErrorListener( new SearchNetErrorListener() {
                @Override
                public void a() {
                    Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            mSearchNetErrorView.setVisibility(View.GONE);
        }
        mSearchHeaderListener = new SearchHeaderListener(this);
        mSearchHeaderView.setSearchHeaderListener(mSearchHeaderListener);
    }

}
