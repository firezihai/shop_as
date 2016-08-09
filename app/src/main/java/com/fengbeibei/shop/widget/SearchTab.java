package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fengbeibei.shop.R;

/**
 * SearchTab
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-09 16:40
 */
public class SearchTab  extends FrameLayout{
    private Context mContext;
    public SearchTab(Context context) {
        super(context);
        initView(context);
    }

    public SearchTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public SearchTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_search_sort,this);
    }
}
