package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsEvaluateFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_evaluate_fragment,container,false);
        return layout;
    }
}
