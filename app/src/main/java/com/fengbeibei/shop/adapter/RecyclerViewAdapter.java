package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.bean.Goods;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.utils.DensityUtil;
import com.fengbeibei.shop.utils.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-16 10:30
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private static String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private SearchResultActivity mSearchResultActivity;
    private int mState = 101;
    public List<Goods> mGoodsList;
    private int mPage =0;
    /*ImageLoader*/
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();

    private int VIEW_TYPE_BIG_ITEM = 2;
    private int VIEW_TYPE_SMALL_ITEM = 1;
    private int VIEW_TYPE_LOAD = 3;
    private int VIEW_TYPE_FOOTER = 4;
    private int VIEW_TYPE_NO_DATA = 5;
    private boolean mHasMore = true;
    private int mPageCount;
    private int mViewType = 1;
    private boolean mScrollEnd = false;
    public RecyclerViewAdapter(Context context, SearchResultActivity searchResultActivity) {
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
        mSearchResultActivity.setPage(mPage);
        mGoodsList.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        int itemCount1 = 0;
        int itemCount2 ;
        if(mGoodsList != null){
            itemCount1 = mGoodsList.size();
            if(mPageCount>0){
                itemCount2 =  itemCount1+1;
            }else {
                itemCount2 = itemCount1;
            }
        }else{
            itemCount2 = itemCount1;
        }
      //  Log.i(TAG,"mScrollEnd "+itemCount2+"");
        return itemCount2;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //   Log.i(TAG,"onCreateViewHolder");
        RecyclerViewHolder recyclerViewHolder = null;
        if(viewType == VIEW_TYPE_BIG_ITEM){
            return new SearchBigItemViewHolder(viewType,inflateView(R.layout.layout_search_big_item));
          //  return getBigItemViewHolder(parent,viewType);
        }else if(viewType == VIEW_TYPE_SMALL_ITEM){
           //return getSmallItemViewHolder(parent,viewType);
            return new SearchSmallItemViewHolder(viewType,inflateView(R.layout.layout_search_small_item));
        }else if(viewType == VIEW_TYPE_LOAD){
            return new SearchLoadMoreViewHolder(viewType,inflateView(R.layout.layout_search_load_more_footer));
        }

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //Log.i(TAG,"onBindViewHolder position="+position);
        int type = getItemViewType(position);
        if(type == VIEW_TYPE_BIG_ITEM){
            onBindBigItemViewHolder(holder,position);
        }else if(type == VIEW_TYPE_SMALL_ITEM){
            onBindSmallItemViewHolder(holder,position);
        }else if(type == VIEW_TYPE_LOAD){
            onBindLoadMoreViewHolder(holder,position);
        }
    }

    public View inflateView(int layoutRes){
        return LayoutInflater.from(mContext).inflate(layoutRes,null);
    }

    public void onBindBigItemViewHolder(RecyclerViewHolder holder,int position){
        Goods goods= mGoodsList.get(position);
        SearchBigItemViewHolder bigItemViewHolder = (SearchBigItemViewHolder) holder;
        bigItemViewHolder.mGoodsPrice.setText(goods.getGoodsPrice());
        setImageViewSize(bigItemViewHolder.mGoodsImage);
        mImageLoader.displayImage(goods.getGoodsImageUrl(), bigItemViewHolder.mGoodsImage, mOptions, mAnimateFirstListener);
        bigItemViewHolder.mGoodsName.setText(goods.getGoodsName());
       /* if(goods.getGoodsNUm > 0) {
            bigItemViewHolder.mGoodsNoSale.setVisibility(View.VISIBLE);
        }else{
            bigItemViewHolder.mGoodsNoSale.setVisibility(View.GONE);
        }
        if(goods.getShipFree > 0) {
            bigItemViewHolder.mShipFree.setVisibility(View.VISIBLE);
        }else{
            bigItemViewHolder.mShipFree.setVisibility(View.GONE);
        }*/
        int isMobileSole = goods.isSoleFlag() ? View.VISIBLE : View.GONE;
        int isGroup = goods.isGroupFlag() ? View.VISIBLE : View.GONE;
        int isXianShi = goods.isXianshiFlag() ? View.VISIBLE : View.GONE;
        bigItemViewHolder.mPromotion1.setVisibility(isMobileSole);
        bigItemViewHolder.mPromotion1.setText("手机专享");
        bigItemViewHolder.mPromotion2.setText("团购");
        bigItemViewHolder.mPromotion2.setVisibility(isGroup);
        bigItemViewHolder.mPromotion3.setText("限时促销");
        bigItemViewHolder.mPromotion3.setVisibility(isXianShi);
    }

    public void onBindSmallItemViewHolder(RecyclerViewHolder holder,int position){
        Goods goods= mGoodsList.get(position);
        SearchSmallItemViewHolder itemViewHolder = (SearchSmallItemViewHolder) holder;
        mImageLoader.displayImage(goods.getGoodsImageUrl(), itemViewHolder.mGoodsImage, mOptions, mAnimateFirstListener);
        itemViewHolder.mGoodsName.setText(goods.getGoodsName());
        itemViewHolder.mGoodsPrice.setText(goods.getGoodsPrice());
        itemViewHolder.mEvalCount.setText("共有 "+goods.getEvaluationCount()+" 评论");
         /* if(goods.getGoodsNUm > 0) {
            itemViewHolder.mGoodsNoSale.setVisibility(View.VISIBLE);
        }else{
            itemViewHolder.mGoodsNoSale.setVisibility(View.GONE);
        }
        if(goods.getShipFree > 0) {
            itemViewHolder.mShipFree.setVisibility(View.VISIBLE);
        }else{
            itemViewHolder.mShipFree.setVisibility(View.GONE);
        }*/
        int isMobileSole = goods.isSoleFlag() ? View.VISIBLE : View.GONE;
        int isGroup = goods.isGroupFlag() ? View.VISIBLE : View.GONE;
        int isXianShi = goods.isXianshiFlag() ? View.VISIBLE : View.GONE;
        itemViewHolder.mPromotion1.setVisibility(isMobileSole);
        itemViewHolder.mPromotion1.setText("手机专享");
        itemViewHolder.mPromotion2.setText("团购");
        itemViewHolder.mPromotion2.setVisibility(isGroup);
        itemViewHolder.mPromotion3.setText("限时促销");
        itemViewHolder.mPromotion3.setVisibility(isXianShi);
    }

    public void onBindLoadMoreViewHolder(RecyclerViewHolder holder,int position){
        SearchLoadMoreViewHolder loadMoreViewHolder = (SearchLoadMoreViewHolder) holder;
     //   Log.i(TAG,"onBindLoadMoreViewHolder mHasMore="+mHasMore);
        if( mPageCount >0 && mHasMore){
            loadMoreViewHolder.mProgressBarLayout.setVisibility(View.VISIBLE);
            loadMoreViewHolder.mLoadError.setVisibility(View.GONE);
            loadMoreViewHolder.mNoMore.setVisibility(View.GONE);
        }else if(!mHasMore && mPageCount>0){
            loadMoreViewHolder.mProgressBarLayout.setVisibility(View.GONE);
            loadMoreViewHolder.mLoadError.setVisibility(View.GONE);
            loadMoreViewHolder.mNoMore.setVisibility(View.VISIBLE);
        }else if(mPageCount<=0){
            loadMoreViewHolder.mProgressBarLayout.setVisibility(View.GONE);
            loadMoreViewHolder.mLoadError.setVisibility(View.VISIBLE);
            loadMoreViewHolder.mNoMore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() -1  && mPageCount>0 ){
            return VIEW_TYPE_LOAD;
       /* }else if(mGoodsList.size() == position && isHasMore()){
            return VIEW_TYPE_LOAD;*/
        }else if(getItemCount() == position && !isHasMore()){
          //  return VIEW_TYPE_FOOTER;
        }else if(position == 0 ){
        //    return VIEW_TYPE_NO_DATA;
        }
        return mViewType;
    }


    public void setHasMore(boolean hasMore){
        mHasMore = hasMore;
    }

    public boolean isHasMore(){
        return mHasMore;
    }

    public void setPageCount(long pageCount) {
        mPageCount = (int)pageCount;
    }

    private void setImageViewSize(ImageView imageView){
        int width =  (ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext,5.0f) ) /2;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)imageView.getLayoutParams();
        params.width = width;
        params.height = width;

    }
    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if(holder instanceof SearchLoadMoreViewHolder){
            layoutParams.setFullSpan(true);
        }
    }

    public void setViewType(int viewType) {
        mViewType = viewType;
    }

    public void clearData(){
        mGoodsList.clear();
    }

}
