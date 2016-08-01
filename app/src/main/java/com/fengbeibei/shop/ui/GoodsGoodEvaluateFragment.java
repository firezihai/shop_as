package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;

/**
 * Created by thinkpad on 2016-08-01.
 */
public class GoodsGoodEvaluateFragment extends Fragment {
    private String mGoodsId;
    public static GoodsGoodEvaluateFragment newInstance(String goodsId){
        GoodsGoodEvaluateFragment goodsGoodEvaluateFragment = new GoodsGoodEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId",goodsId);
        goodsGoodEvaluateFragment.setArguments(bundle);
        return goodsGoodEvaluateFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_evaluate_all_fragment,container,false);

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0";
    }
}
