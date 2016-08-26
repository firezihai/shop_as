package com.fengbeibei.shop.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.fragment.AccountManageFragment;
import com.fengbeibei.shop.fragment.AddressManageFragment;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 19:57
 */
public class AccountManageActivity extends BaseActivity{
    public static final String DISPLAY_FRAGMENT_TYPE = "bundle_fragment_type";
    public static final int ACCOUNT_MANAGE = 1;
    public static final int ADDRESS_MANAGE = 2;
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
