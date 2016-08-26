package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.view.View;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.AccountManageActivity;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 21:22
 */
public class AddressManageFragment extends BaseFragment implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_address_manage;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddHead(true);
    }

    @Override
    public void initView() {
        setHeadTitle(R.string.address_manage);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {

    }
}
