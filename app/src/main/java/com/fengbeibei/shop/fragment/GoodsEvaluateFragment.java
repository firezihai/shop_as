package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.GoodsEvaluateFragmentPagerAdapter;
import com.fengbeibei.shop.fragment.Base.GoodsBaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsEvaluateFragment extends GoodsBaseFragment {
    private String TAG = "GoodsEvaluateFragment";
    private String mGoodsId;
    private ArrayList<Fragment> mFragments = null;
    private GoodsEvaluateFragmentPagerAdapter mAdapter;
    @BindView(R.id.evalViewPager)
    ViewPager mEvalViewPager;

    private GoodsAllEvaluateFragment mAllEvaluateFragment;
    private GoodsGoodEvaluateFragment mGoodEvaluateFragment;
    public static GoodsEvaluateFragment newInstance(String goodsId){
        GoodsEvaluateFragment fragment = new GoodsEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", goodsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0";

    }


    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        mAllEvaluateFragment = GoodsAllEvaluateFragment.newInstance(mGoodsId);
        mGoodEvaluateFragment = GoodsGoodEvaluateFragment.newInstance(mGoodsId);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(mAllEvaluateFragment);
        mFragments.add(mGoodEvaluateFragment);
        GoodsEvaluateFragmentPagerAdapter adapter = new GoodsEvaluateFragmentPagerAdapter(getChildFragmentManager(),mFragments);
        //mAdapter.setData(mFragments);
        mEvalViewPager.setAdapter(adapter);
        mEvalViewPager.setCurrentItem(0);
    }

    @Override
    public void lazyLoad() {
    }

    @Override
    public void setUpdate(String data) {
        mGoodsId = data;
        mGoodEvaluateFragment.setUpdate(data);
        mAllEvaluateFragment.setUpdate(data);
    }

}
