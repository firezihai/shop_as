package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.AccountManageActivity;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 20:08
 */
public class AccountManageFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.rl_address_manage)
    RelativeLayout mAddressManage;
    @BindView(R.id.rl_modify_password)
    RelativeLayout mModifyPassword;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_manage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddHead(true);
    }


    @Override
    public void initView() {
        setHeadTitle(R.string.account);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mAddressManage.setOnClickListener(this);
        mModifyPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_address_manage:
                IntentHelper.accountManage(getActivity(), AccountManageActivity.ADDRESS_MANAGE);
                break;
        }
    }
}

