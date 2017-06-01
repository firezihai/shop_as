package com.zihai.shop.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.zihai.shop.R;
import com.zihai.shop.fragment.AccountManageFragment;
import com.zihai.shop.fragment.AddressAddFragment;
import com.zihai.shop.fragment.AddressEditFragment;
import com.zihai.shop.fragment.AddressManageFragment;
import com.zihai.shop.fragment.Base.BaseFragment;

/**
 * 账户管理宿主Activity.
 * 包括以下fragment:账户管理、收货地址列表、辑地址、新增地址
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 19:57
 */
public class AccountManageActivity extends BaseActivity{
    public static final String DISPLAY_FRAGMENT_TYPE = "bundle_fragment_type";
    public static final int ACCOUNT_MANAGE = 1;
    public static final int ADDRESS_MANAGE = 2;
    public static final int ADDRESS_EDIT = 3;
    public static final int ADDRESS_ADD = 4;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initView() {

    }

    @Override
    public void init(Bundle savedInstancedState) {
        super.init(savedInstancedState);
        int displayFragment = getIntent().getIntExtra(DISPLAY_FRAGMENT_TYPE,ACCOUNT_MANAGE);
        BaseFragment fragment = null;
        switch (displayFragment){
            case ACCOUNT_MANAGE:
                fragment = new AccountManageFragment();
                break;
            case ADDRESS_MANAGE:
                fragment = new AddressManageFragment();
                break;
            case ADDRESS_EDIT:
                fragment = new AddressEditFragment();
                break;
            case ADDRESS_ADD:
                fragment = new AddressAddFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void initData() {
        super.initData();
    }


}
