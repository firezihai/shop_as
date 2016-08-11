package com.fengbeibei.shop.activity;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.callback.SearchHeaderListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.interf.SearchHeaderInterface;
import com.fengbeibei.shop.interf.SearchNetErrorListener;
import com.fengbeibei.shop.interf.SearchTabInterface;
import com.fengbeibei.shop.utils.NetUtil;
import com.fengbeibei.shop.widget.SearchHeaderView;
import com.fengbeibei.shop.widget.SearchNetErrorView;
import com.fengbeibei.shop.widget.SearchTab;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;

public class SearchResultActivity extends BaseActivity implements SearchTabInterface{
    @BindView(R.id.netErrorView)
    SearchNetErrorView mSearchNetErrorView;
    @BindView(R.id.searchHeader)
    SearchHeaderView mSearchHeaderView;

    @BindView(R.id.searchTab)
    SearchTab mSearchTab;
    private String mCateId;
    private String mSearchKeyword;
    /**
     * 排序类形
     * 1：综合排序；2：销量排序；3：价格排序；4；评价排序；5：时间排序
     */
    private int mOrderType = 1;
    /**
     * 排序方式 ASC、DESC
     */
    private String mOrder = "DESC";
    /**
     * 是否自营商品
     */
    private boolean mOwnShop = false;
    /**
     * 是否为促销商品
     */
    private boolean mIsPromotion = false;
    /**
     * SearchHeaderView回调
     */
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
        if(mCateId != null && mCateId.equals("")){
            url = url + "&gc_id="+mCateId;
        }
        if(mSearchKeyword != null && mSearchKeyword.equals("")){
            url = url + "&keyword="+mSearchKeyword;
        }
       if(mOrder.equals("ASC")){
           url += "&order=1";
        }
        if(mOwnShop){
            url +="&ownShop=1";
        }
        if(mIsPromotion){
            url +="is_promotion=1";
        }
        url +="&type="+mOrderType;

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
        mCateId = getIntent().getStringExtra("gcId");
        mSearchKeyword = getIntent().getStringExtra("keyword");
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
        mSearchTab.setSearchTabListener(this);
    }


    @Override
    public void mixSort() {
        mOrderType = 2;
        initData();
    }

    @Override
    public void priceUp() {
        mOrderType = 3;
        mOrder = "ASC";
        initData();
    }

    @Override
    public void priceDown() {
        mOrderType = 3;
        mOrder = "DESC";
        initData();
    }

    @Override
    public void evalUp() {
        mOrderType = 4;
        mOrder = "ASC";
        initData();
    }

    @Override
    public void evalDown() {
        mOrderType = 4;
        mOrder = "DESC";
        initData();
    }

    @Override
    public void dateSort() {
        mOrderType = 5;
        mOrder = "DESC";
        initData();
    }

    @Override
    public void isOwnShop() {
        mOwnShop = mSearchTab.isOwnShop();
        initData();
    }

    @Override
    public void isPromotion() {
        mIsPromotion = mSearchTab.isPromotion();
        initData();
    }

    @Override
    public void SalesSort() {

    }
}
