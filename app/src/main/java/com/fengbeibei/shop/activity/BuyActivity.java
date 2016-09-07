package com.fengbeibei.shop.activity;

import android.view.View;

import com.fengbeibei.shop.R;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-07 20:14
 */
public class BuyActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy;
    }
    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.confirm_order);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
