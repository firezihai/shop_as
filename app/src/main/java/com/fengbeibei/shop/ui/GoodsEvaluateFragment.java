package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
public class GoodsEvaluateFragment extends GoodsBaseFragment implements GoodsEvaluateFragmentPagerAdapter.OnReloadListener{

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayoutId(), container, false);

       // initData();
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
    }

    @Override
    public void onReload() {
        mFragments = null;
        mFragments = new ArrayList<Fragment>();
        mFragments.add(GoodsAllEvaluateFragment.newInstance(mGoodsId));
        mFragments.add(GoodsGoodEvaluateFragment.newInstance(mGoodsId));
        mAdapter.setData(mFragments);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_fragment;
    }

    @Override
    public void initData() {

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
    }

    @Override
    public void setGoodsId(String goodsId) {
        mGoodsId = goodsId;
    }

    @Override
    public void onUpdateUI() {
        onReload();
    }
}
