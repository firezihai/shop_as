package com.zihai.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zihai.shop.R;
import com.zihai.shop.interf.SearchNetErrorListener;

/**
 * SearchNetErrorLayout
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 17:28
 */
public class SearchNetErrorView extends FrameLayout{
    private SearchNetErrorListener mSearchNetErrorListener;
    private Context mContext;
    public SearchNetErrorView(Context context) {
        super(context);
        initView(context);
    }

    public SearchNetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
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
        ((Button)findViewById(R.id.retry)).setOnClickListener(new SearchNetErrorViewClickListener(this));
    }

    public Context getCurrentContext(){
        return mContext;
    }


    public void setSearchNetErrorListener(SearchNetErrorListener SearchNetErrorListener){
        mSearchNetErrorListener = SearchNetErrorListener;
    }

    public SearchNetErrorListener getSearchNetErrorListener(){
        return mSearchNetErrorListener;
    }

}
