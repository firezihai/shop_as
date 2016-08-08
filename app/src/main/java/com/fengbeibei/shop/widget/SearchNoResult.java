package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fengbeibei.shop.R;

/**
 * SerachNoResult
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 15:31
 */
public class SearchNoResult extends FrameLayout{
    public SearchNoResult(Context context) {
        super(context);
        inflaterView(context);
    }

    public SearchNoResult(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflaterView(context);
    }

    public SearchNoResult(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflaterView(context);
    }

    @SuppressLint("NewApi")
    public SearchNoResult(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflaterView(context);
    }

    public void inflaterView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_search_no_result, this);
    }

    public void initView(){

    }
}
