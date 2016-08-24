package com.fengbeibei.shop.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fengbeibei.shop.R;

/**
 * Created by Administrator on 2016/7/30.
 */
public class MyListView extends ListView{
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
        addFooterView(mFooterView);
        mFooterView.setVisibility(View.GONE);
    }
    @Override
    public void setOnScrollListener(OnScrollListener l) {
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
        if(footerEnd){
            footerVisibility(View.VISIBLE);
            mListScrollCallListener.updateData();
        }
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
