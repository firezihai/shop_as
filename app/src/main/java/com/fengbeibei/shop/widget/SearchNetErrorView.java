package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.ViewInterface;

/**
 * SearchNetErrorLayout
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 17:28
 */
public class SearchNetErrorView extends FrameLayout{
    private ViewInterface mViewInterface;
    public SearchNetErrorView(Context context) {
        super(context);
        initView(context);
    }

    public SearchNetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchNetErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public SearchNetErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_search_net_error, this);
        ((Button)findViewById(R.id.retry)).setOnClickListener(new Test(this));
    }

    public void setViewInterface(ViewInterface viewInterface){
        mViewInterface = viewInterface;
    }
}
