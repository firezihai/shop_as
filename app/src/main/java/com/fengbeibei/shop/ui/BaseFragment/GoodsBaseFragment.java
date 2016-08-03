package com.fengbeibei.shop.ui.BaseFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.interf.BaseFragmentInterface;
import com.fengbeibei.shop.interf.GoodsFragmentListener;

/**
 * Created by zihai on 2016-08-02.
 */
public class GoodsBaseFragment extends Fragment implements BaseFragmentInterface{
    private LayoutInflater mInflater;
    protected int getLayoutId(){
        return 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    protected View inflaterView(int resId){
        return mInflater.inflate(resId,null);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    public void setGoodsId(String goodsId){

    }

    public void onUpdateUI(){}
}
