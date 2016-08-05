package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.GoodsEvaluateFragmentPagerAdapter;
import com.fengbeibei.shop.ui.BaseFragment.GoodsBaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayoutId(), container, false);

     //   initView(layout);
        return layout;
    }*/


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
      //  initView(view);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_fragment;
    }

    @Override
    public void initData() {
     /*   mFragments = new ArrayList<Fragment>();
        mFragments.add(GoodsAllEvaluateFragment.newInstance(mGoodsId));
        mFragments.add(GoodsGoodEvaluateFragment.newInstance(mGoodsId));
        mAdapter.setData(mFragments);*/
       mFragments = new ArrayList<Fragment>();
        mFragments.add(GoodsAllEvaluateFragment.newInstance(mGoodsId));
        mFragments.add(GoodsGoodEvaluateFragment.newInstance(mGoodsId));
        GoodsEvaluateFragmentPagerAdapter adapter = new GoodsEvaluateFragmentPagerAdapter(getChildFragmentManager(),mFragments);
        //mAdapter.setData(mFragments);
        mEvalViewPager.setAdapter(adapter);
        mEvalViewPager.setCurrentItem(0);

    }

    @Override
    public void initView(View view) {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(GoodsAllEvaluateFragment.newInstance(mGoodsId));
        mFragments.add(GoodsGoodEvaluateFragment.newInstance(mGoodsId));
        GoodsEvaluateFragmentPagerAdapter adapter = new GoodsEvaluateFragmentPagerAdapter(getChildFragmentManager(),mFragments);
        //mAdapter.setData(mFragments);
        mEvalViewPager.setAdapter(adapter);
        mEvalViewPager.setCurrentItem(0);
        mEvalViewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void lazyLoad() {
        if(!mVisible || !mDelayLoad){
            return ;
        }
        initData();
    }

    @Override
    public void setUpdate(String data) {
        mGoodsId = data;
        mDelayLoad = true;
    }

}
