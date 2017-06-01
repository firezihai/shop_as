package com.zihai.shop.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zihai.shop.R;

/**
 * Created by Administrator on 2016/7/30.
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener{
    private LinearLayout mFooterView;
    private ScrollCallListener mListScrollCallListener;
    public MyListView(Context context) {
        super(context);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    @TargetApi(21)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        mFooterView = (LinearLayout) View.inflate(context, R.layout.listview_footer, null);
        addFooterView(mFooterView,null,false);
        mFooterView.setVisibility(View.GONE);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(getAdapter() == null && getAdapter().getCount() == 0){
            return ;
        }
        boolean footerEnd = false;
        try {
            if(getPositionForView(mFooterView) == getLastVisiblePosition()){
                footerEnd = true;
            }else{
                footerEnd = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(footerEnd && scrollState == OnScrollListener.SCROLL_STATE_IDLE){
            footerVisibility(View.VISIBLE);
            if(mListScrollCallListener != null) {
                mListScrollCallListener.updateData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public void setScrollCallListener(ScrollCallListener listener){
        mListScrollCallListener = listener;
    }

    public void setFooterViewText(String text){
        ((TextView)mFooterView.findViewById(R.id.upToLoadText)).setText(text);
        (mFooterView.findViewById(R.id.progressbar)).setVisibility(View.GONE);
    }
    public void footerVisibility(int visibility){
        mFooterView.setVisibility(visibility);
    }
    public abstract interface ScrollCallListener{
        public abstract void updateData();
    }
}
