package com.fengbeibei.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.interf.SearchTabInterface;

import butterknife.BindView;

/**
 * SearchActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-10 15:24
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBackBtn;
    @BindView(R.id.searchEdit)
    EditText mSearchEdit;
    @BindView(R.id.tv_search)
    TextView mSearchBtn;
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
        mBackBtn.setOnClickListener(this);
        mSearchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.out.println("tv_search");
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                System.out.println("tv_search");
                String keyword = mSearchEdit.getText().toString();
                IntentHelper.searchResult(this,keyword);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
           onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBack(){
      //  if(mSearchEdit.)
    }
}
