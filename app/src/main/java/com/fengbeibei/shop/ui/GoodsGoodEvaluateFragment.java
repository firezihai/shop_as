package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.ui.BaseFragment.ViewPagerFragment;

/**
 * Created by thinkpad on 2016-08-01.
 */
public class GoodsGoodEvaluateFragment extends ViewPagerFragment {
    private String mGoodsId;
    private View mRootView;
    public static GoodsGoodEvaluateFragment newInstance(String goodsId){
        GoodsGoodEvaluateFragment goodsGoodEvaluateFragment = new GoodsGoodEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId",goodsId);
        goodsGoodEvaluateFragment.setArguments(bundle);
        return goodsGoodEvaluateFragment;
    }
    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.goods_evaluate_all_fragment,container,false);
        }


        return mRootView;
    }*/

    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_all_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0";
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void lazyLoad() {
        if(!mVisible || mDelayLoad){
            return;
        }
        initData();
    }


}
