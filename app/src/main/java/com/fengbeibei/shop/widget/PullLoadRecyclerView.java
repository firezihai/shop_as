package com.fengbeibei.shop.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.callback.RecyclerViewOnScrollListener;

/**
 * PullLoadRecyclerView
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 8:52
 */
public class PullLoadRecyclerView extends RelativeLayout{
    private RelativeLayout mLayout;
    private StaggeredGridLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerViewOnScrollListener(this);
    public PullLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        RelativeLayout layout = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.layout_search_recycleview,null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addView(layout,layoutParams);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.pull_load_recycleView);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void setRecycleViewListener(){

    }

    public void setSpanCount(int spanCount){
        mLayoutManager.setSpanCount(spanCount);
    }

    public int getSpanCount(){
        return mLayoutManager.getSpanCount();
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void setLayoutManager(StaggeredGridLayoutManager layoutManager){
        mLayoutManager = layoutManager;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter(){

    }
}
