package com.fengbeibei.shop.activity;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.RecyclerViewAdapter;
import com.fengbeibei.shop.bean.Goods;
import com.fengbeibei.shop.callback.PullLoadRecyclerViewOnScrollListener;
import com.fengbeibei.shop.callback.SearchHeaderListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.interf.SearchNetErrorListener;
import com.fengbeibei.shop.interf.SearchTabInterface;
import com.fengbeibei.shop.utils.NetUtil;
import com.fengbeibei.shop.widget.PullLoadRecyclerView;
import com.fengbeibei.shop.widget.RecyclerViewItemDecoration;
import com.fengbeibei.shop.widget.SearchHeaderView;
import com.fengbeibei.shop.widget.SearchNetErrorView;
import com.fengbeibei.shop.widget.SearchNoResult;
import com.fengbeibei.shop.widget.SearchPageView;
import com.fengbeibei.shop.widget.SearchTab;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchResultActivity extends BaseActivity implements SearchTabInterface{

    private ViewHolder mViewHolder;
    private String mCateId;
    private String mSearchKeyword;
    private int mPage = 0;
    private long mPageCount = 0;
    private boolean mHasMore = true;
    private boolean mLoadState = true;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private PullLoadRecyclerViewOnScrollListener mScrollCallback = new PullLoadRecyclerViewOnScrollListener(this);
    /**
     * 排序类形
     * 1：综合排序；2：销量排序；3：价格排序；4；评价排序；5：时间排序
     */
    private int mOrderType = 1;
    /**
     * 排序方式 ASC、DESC
     */
    private String mOrder = "DESC";
    /**
     * 是否自营商品
     */
    private boolean mOwnShop = false;
    /**
     * 是否为促销商品
     */
    private boolean mIsPromotion = false;
    /**
     * SearchHeaderView回调
     */
    private SearchHeaderListener mSearchHeaderListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void init(Bundle savedInstancedState) {
        super.init(savedInstancedState);

    }

    @Override
    public void initData() {
        String url = Constants.SEARCH_GOODS_LIST_URL+"&page="+Constants.PAGESIZE+"&curpage="+mPage;
        if(mCateId != null && !mCateId.equals("")){
            url = url + "&gc_id="+mCateId;
        }
        if(mSearchKeyword != null && mSearchKeyword.equals("")){
            url = url + "&keyword="+mSearchKeyword;
        }
       if(mOrder.equals("ASC")){
           url += "&order=1";
        }
        if(mOwnShop){
            url +="&ownShop=1";
        }
        if(mIsPromotion){
            url +="is_promotion=1";
        }
        url +="&key="+mOrderType;
        Log.i("RecyclerViewAdapter", url);
        mLoadState = true;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                mLoadState = false;
                if (response.what == HttpStatus.SC_OK) {
                    Bundle bundle = response.getData();
                    //mHasMore = bundle.getBoolean(HttpClientHelper.HASMORE);
                    //     mPageCount = bundle.getLong(HttpClientHelper.COUNT);

                    try {
                        String json = (String) response.obj;
                        JSONObject obj = new JSONObject(json);
                        String goodsListJson = obj.getString("list");
                        mPageCount = obj.getLong("pagecount");
                        List<Goods> goodsList = Goods.arrayListBeanFromData(goodsListJson);
                        mRecyclerViewAdapter.addData(goodsList);
                        if ((mPageCount * Integer.parseInt(Constants.PAGESIZE ))> (Integer.parseInt(Constants.PAGESIZE ) * mPage)) {
                            mHasMore = true;
                        }else{
                            mHasMore = false;
                        }
                        mRecyclerViewAdapter.setHasMore(mHasMore);
                        mRecyclerViewAdapter.setPageCount(mPageCount);
                        mViewHolder.mSearchPageView.setText(mPage + "", mPageCount + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void initView() {
        mViewHolder = new ViewHolder(this);
        mCateId = getIntent().getStringExtra("gcId");
        mSearchKeyword = getIntent().getStringExtra("keyword");
        NetworkInfo networkInfo = NetUtil.getActiveNetwork(this);
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            mViewHolder.mSearchNetErrorView.setVisibility(View.VISIBLE);
            mViewHolder.mSearchNetErrorView.setSearchNetErrorListener( new SearchNetErrorListener() {
                @Override
                public void retry() {
                    Toast.makeText(SearchResultActivity.this,"刷新成功",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            mViewHolder. mSearchNetErrorView.setVisibility(View.GONE);
        }
        mSearchHeaderListener = new SearchHeaderListener(this);
        mViewHolder.mSearchHeaderView.setSearchHeaderListener(mSearchHeaderListener);
        mViewHolder.mSearchHeaderView.setSearchKeyword(mSearchKeyword);
        mViewHolder.mSearchTab.setSearchTabListener(this);

        mRecyclerViewAdapter = new RecyclerViewAdapter(this,this);
        mRecyclerViewAdapter.setHasMore(mHasMore);
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String goodsId) {
                IntentHelper.goodsDetail(SearchResultActivity.this,goodsId);
            }
        });
        mViewHolder.mPullLoadRecyclerView.setAdapter(mRecyclerViewAdapter);
        mViewHolder.mPullLoadRecyclerView.setOnScrollCallback(mScrollCallback);
        postSearchTitleHeight();
        mViewHolder.mPullLoadRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(PullLoadRecyclerView.TYPE_LIST,
                StaggeredGridLayoutManager.VERTICAL));
        mViewHolder.mPullLoadRecyclerView.setItemDecoration(new RecyclerViewItemDecoration());
        initData();
    }


    @Override
    public void mixSort() {
        mOrderType = 2;
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void priceUp() {
        mOrderType = 3;
        mOrder = "ASC";
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void priceDown() {
        mOrderType = 3;
        mOrder = "DESC";
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void evalUp() {
        mOrderType = 4;
        mOrder = "ASC";
        mPage = 0;
        initData();
    }

    @Override
    public void evalDown() {
        mOrderType = 4;
        mOrder = "DESC";
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void dateSort() {
        mOrderType = 5;
        mOrder = "DESC";
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void isOwnShop() {
        mPage = 0;
        mOwnShop = mViewHolder.mSearchTab.isOwnShop();
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void isPromotion() {
        mPage = 0;
        mIsPromotion = mViewHolder.mSearchTab.isPromotion();
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void SalesSort() {
        mPage = 0;
        mRecyclerViewAdapter.clearData();
        mOrderType = 1;
        initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBack();
        }
        //return super.onKeyDown(keyCode, event);
        return true;
    }

    public void onBack(){
        if(mViewHolder.mSearchTab.isSortLayoutVisibility()){
            mViewHolder.mSearchTab.hideSortLayout();
            return;
        }
        finish();
    }

    /**
     * 点击搜索框时调用
     * @param searchResultActivity
     */
    public static void onSearch(SearchResultActivity searchResultActivity){
        searchResultActivity.search();
    }

    /**
     * 点击搜索框时调用
     */
    private  void search(){
        String keyword = mViewHolder.mSearchHeaderView.getSearchKeyword();
        IntentHelper.search(this, keyword);
    }

    private void postSearchTitleHeight(){
        mViewHolder.mSearchTitle.post(new Runnable() {
            @Override
            public void run() {
                mViewHolder.mPullLoadRecyclerView.setPaddingTop(mViewHolder.mSearchTitle.getHeight());
            }
        });
    }

    public static ViewHolder getViewHolder(SearchResultActivity activity) {
        return activity.mViewHolder;
    }

    public class ViewHolder{
        private SearchResultActivity mSearchResultActivity;
        public PullLoadRecyclerView mPullLoadRecyclerView;
        public SearchNetErrorView mSearchNetErrorView;
        public SearchNoResult mSearchNoResult;
        public SearchPageView mSearchPageView;
        public LinearLayout mSearchTitle;
        public SearchTab mSearchTab;
        SearchHeaderView mSearchHeaderView;
        public ViewHolder(SearchResultActivity searchResultActivity) {
            mSearchResultActivity = searchResultActivity;
            initView();
        }

        public void initView(){
            mPullLoadRecyclerView = (PullLoadRecyclerView)mSearchResultActivity.findViewById(R.id.pull_search_list);
            mSearchNetErrorView = (SearchNetErrorView) mSearchResultActivity.findViewById(R.id.netErrorView);
            mSearchPageView = (SearchPageView) mSearchResultActivity.findViewById(R.id.search_page_view);
            mSearchNoResult = (SearchNoResult) mSearchResultActivity.findViewById(R.id.search_no_result_view);
            mSearchTitle = (LinearLayout) mSearchResultActivity.findViewById(R.id.ll_search_title);
            mSearchHeaderView = (SearchHeaderView) mSearchResultActivity.findViewById(R.id.searchHeader);
            mSearchTab = (SearchTab) mSearchResultActivity.findViewById(R.id.searchTab);
        }
    }

    public static void updateData(SearchResultActivity activity,int scrollState){
     //   Log.i("RecyclerOnScroll","mState"+scrollState+" ");
        if(scrollState != RecyclerView.SCROLL_STATE_IDLE && !activity.mLoadState && activity.mPage<activity.mPageCount) {
            activity.initData();
        }
    }

    public void setPage(int page) {
        mPage = page;
    }


}
