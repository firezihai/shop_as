package com.fengbeibei.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String mSearchKeyword;
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
        mSearchKeyword = getIntent().getStringExtra("keyword");
        if(!TextUtils.isEmpty(mSearchKeyword)) {
            mSearchEdit.setText(mSearchKeyword);
            mSearchEdit.setSelection(mSearchKeyword.length());
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("tv_search");
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                String keyword = mSearchEdit.getText().toString();
                if(TextUtils.isEmpty(keyword)) {
                    Toast.makeText(this,"请输入你在搜索的关键词！",Toast.LENGTH_SHORT).show();
                }else{
                    IntentHelper.searchResult(this, keyword);
                }
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
        hideInputMethod();
        if(TextUtils.isEmpty(mSearchKeyword)){
            finish();
            return;
        }
        IntentHelper.home(this,0);
    }

    public void hideInputMethod(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mSearchEdit.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
