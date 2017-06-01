package com.zihai.shop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zihai.shop.R;

/**
 * SearchPageView
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-17 16:55
 */
public class SearchPageView extends FrameLayout{
    private TextView mCurrentPage;
    private TextView mPageCount;
    public SearchPageView(Context context) {
        super(context);
        initView(context);
    }

    public SearchPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_search_page_view, this);
        mCurrentPage = (TextView) findViewById(R.id.tv_current_page);
        mPageCount = (TextView) findViewById(R.id.tv_page_count);
    }

    public void setText(String currentPage,String pageCount){
        mCurrentPage.setText(currentPage);
        mPageCount.setText(pageCount);
    }
}
