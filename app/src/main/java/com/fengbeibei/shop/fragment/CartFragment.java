package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

public class CartFragment extends BaseFragment {
	private MyApplication mApplication;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        mInflater = inflater;
		View cartLayout = inflater.inflate(getLayoutId(), container, false);
		 mApplication = MyApplication.getInstance();
		return cartLayout;
	}

    @Override
    protected int getLayoutId() {
        return R.layout.cart;
    }

    @Override
    public void initData() {
        HttpClientHelper.asynGet(Constants.CART_LIST_URL, new CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(Exception e) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("ucenterFragment", "onResume");
		if(mApplication.isLogin()){

			initData();
		}
		
	}
}
