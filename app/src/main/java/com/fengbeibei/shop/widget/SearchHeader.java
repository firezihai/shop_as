package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fengbeibei.shop.R;

/**
 * SearchHeader
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-09 16:51
 */
public class SearchHeader extends FrameLayout{
    private Context mContext;
    public SearchHeader(Context context) {
        super(context);
        initView(context);
    }

    public SearchHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public SearchHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_search_title, this);
    }
}
