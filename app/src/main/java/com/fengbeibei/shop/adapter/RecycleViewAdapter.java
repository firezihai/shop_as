package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.bean.Goods;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;
import java.util.List;

/**
 * RecycleViewAdapter
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 10:30
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private Context mContext;
    private SearchResultActivity mSearchResultActivity;
    public List<Goods> mGoodsList;
    private int mPage =0;
    /*ImageLoader*/
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    public RecycleViewAdapter(Context context, SearchResultActivity searchResultActivity) {
        mContext = context;
        mSearchResultActivity = searchResultActivity;
    }
    public void addData(List<Goods> data){
        if(mGoodsList == null){
            mGoodsList = new ArrayList<Goods>();
        }
        if((mPage == 0) && !mGoodsList.isEmpty()){
            mGoodsList.clear();
        }
        mPage +=1;
        mGoodsList.addAll(data);
    }
    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder recyclerViewHolder = null;
        if(viewType == 1){
            return getBigItemViewHolder(parent,viewType);
        }else if(viewType == 2){
            return getSmallItemViewHolder(parent,viewType);
        }

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == 1){
            onBindBigItemViewHolder(holder,position);
        }
    }

    public SearchBigItemViewHolder getBigItemViewHolder(ViewGroup parent,int viewType){
        return new SearchBigItemViewHolder(viewType,InflateView(R.layout.layout_search_big_item));
    }

    public SearchSmallItemViewHolder getSmallItemViewHolder(ViewGroup parent,int viewType){
        return new SearchSmallItemViewHolder(viewType,InflateView(R.layout.layout_search_small_item));
    }

    public View InflateView(int layoutRes){
        return LayoutInflater.from(mContext).inflate(layoutRes,null);
    }

    public void onBindBigItemViewHolder(RecyclerViewHolder holder,int position){
        Goods goods= mGoodsList.get(position);
        SearchBigItemViewHolder bigItemViewHolder = (SearchBigItemViewHolder) holder;
        bigItemViewHolder.mGoodsPrice.setText(goods.getGoodsPrice());
        mImageLoader.displayImage(goods.getGoodsImageUrl(),bigItemViewHolder.mGoodsImage,mOptions,mAnimateFirstListener);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 1){
            return 1;
        }else if(position == 2){
            return 2;
        }
        return 1;
    }

}
