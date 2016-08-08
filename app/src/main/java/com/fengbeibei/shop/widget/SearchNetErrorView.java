package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fengbeibei.shop.R;

/**
 * SearchNetErrorLayout
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 17:28
 */
public class SearchNetErrorView extends FrameLayout{
    public SearchNetErrorView(Context context) {
        super(context);
    }

    public SearchNetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchNetErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public SearchNetErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_search_no_result, this);
    }
}
