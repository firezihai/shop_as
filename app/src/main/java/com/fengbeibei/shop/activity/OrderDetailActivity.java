package com.fengbeibei.shop.activity;

import android.view.View;

import com.fengbeibei.shop.R;

/**
 * OrderDetailActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-25 11:38
 */
public class OrderDetailActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.order_detail);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
