package com.fengbeibei.shop.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.fengbeibei.shop.adapter.RecyclerViewAdapter;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.utils.DensityUtil;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 23:32
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration{

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        RecyclerViewAdapter adapter = (RecyclerViewAdapter)parent.getAdapter();
        if(spanCount == 2){
            int space = DensityUtil.dp2px(MyApplication.getContext(),5.0f);
            if(adapter.getItemCount()<0){
                if(itemPosition%2 == 0) {
                    outRect.set(0, space, 0, 0);
                    return;
                }
            }
            if (itemPosition%2 == 0){
                outRect.set(space, space, 0, 0);
            }
        }else{
            return;
        }
    }

    public RecyclerViewItemDecoration() {
        super();
    }

   public int getSpanCount(RecyclerView recyclerView){
       RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
       if(layoutManager instanceof StaggeredGridLayoutManager){
        return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
       }
       return -1;
   }
}
