package com.fengbeibei.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.SearchTabInterface;

import butterknife.BindView;

/**
 * SearchActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-10 15:24
 */
public class SearchActivity extends BaseActivity implements SearchTabInterface{
    @BindView(R.id.back)
    ImageView backBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init(Bundle savedInstancedState) {
        super.init(savedInstancedState);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void mixSort() {

    }

    @Override
    public void priceDown() {

    }

    @Override
    public void evalUp() {

    }

    @Override
    public void evalDown() {

    }

    @Override
    public void dateSort() {

    }

    @Override
    public void isSelfShop() {

    }

    @Override
    public void isPromotion() {

    }

    @Override
    public void SalesSort() {

    }
}
