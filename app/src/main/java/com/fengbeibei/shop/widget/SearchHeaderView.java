package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.callback.SearchHeaderListener;
import com.fengbeibei.shop.interf.SearchHeaderInterface;

/**
 * SearchHeader
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-09 16:51
 */
public class SearchHeaderView extends FrameLayout implements View.OnClickListener{
    private Context mContext;
    private SearchHeaderListener mSearchHeaderListener;
    public SearchHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public SearchHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public SearchHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_search_result_title, this);
        ImageView imageView = (ImageView)findViewById(R.id.back);
        TextView searchInput = (TextView) findViewById(R.id.searchEdit);
        TextView textView = (TextView) findViewById(R.id.filter);
        imageView.setOnClickListener(this);
        searchInput.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    public void setSearchHeaderListener(SearchHeaderListener searchHeaderListener) {
        mSearchHeaderListener = searchHeaderListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back :
                    mSearchHeaderListener.onBack();
                break;
            case R.id.searchEdit:
                    mSearchHeaderListener.onSearch();
                break;
            case R.id.filter:
                mSearchHeaderListener.onFilter();
                break;
        }
    }
}
