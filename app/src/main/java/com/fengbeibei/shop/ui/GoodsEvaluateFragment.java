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
import com.fengbeibei.shop.interf.GoodsFragmentListener;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsEvaluateFragment extends Fragment implements GoodsFragmentListener,GoodsEvaluateFragmentPagerAdapter.OnReloadListener{

    private String mGoodsId;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
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
        View layout = inflater.inflate(R.layout.goods_evaluate_fragment, container, false);
        ButterKnife.bind(this, layout);
        GoodsAllEvaluateFragment goodsAllEvaluateFragment = GoodsAllEvaluateFragment.newInstance(mGoodsId);
        GoodsGoodEvaluateFragment goodsGoodEvaluateFragment = GoodsGoodEvaluateFragment.newInstance(mGoodsId);
        mFragments.add(goodsAllEvaluateFragment);
        mFragments.add(goodsGoodEvaluateFragment);
        GoodsEvaluateFragmentPagerAdapter adapter = new GoodsEvaluateFragmentPagerAdapter(getChildFragmentManager());

        mEvalViewPager.setAdapter(adapter);
        mEvalViewPager.setCurrentItem(0);
       // initData();
        return layout;
    }



    @Override
    public void onUpdateUI(String data) {
        mGoodsId = data;
        //initData();
    }

    @Override
    public void onReload() {

    }
}
