package com.zihai.shop.activity;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zihai.shop.R;
import com.zihai.shop.adapter.RecyclerViewAdapter;
import com.zihai.shop.bean.Goods;
import com.zihai.shop.callback.PullLoadRecyclerViewOnScrollListener;
import com.zihai.shop.callback.SearchHeaderListener;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.IntentHelper;
import com.zihai.shop.interf.SearchNetErrorListener;
import com.zihai.shop.interf.SearchTabInterface;
import com.zihai.shop.utils.NetUtil;
import com.zihai.shop.widget.PullLoadRecyclerView;
import com.zihai.shop.widget.RecyclerViewItemDecoration;
import com.zihai.shop.widget.SearchHeaderView;
import com.zihai.shop.widget.SearchNetErrorView;
import com.zihai.shop.widget.SearchNoResult;
import com.zihai.shop.widget.SearchPageView;
import com.zihai.shop.widget.SearchTab;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchResultActivity extends BaseActivity implements SearchTabInterface{

    private ViewHolder mViewHolder;
    private String mCateId;
    private String mSearchKeyword;
    private long mPageCount = 0;
    private boolean mHasMore = true;
    private boolean mLoadState = false;
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
        int page = mRecyclerViewAdapter.getPage();
        String url = Constants.SEARCH_GOODS_LIST_URL+"&page="+Constants.PAGESIZE+"&curpage="+page;
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
        mLoadState = true;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                mLoadState = false;
                if (response.what == HttpStatus.SC_OK) {
                    Bundle bundle = response.getData();
                    //mHasMore = bundle.getBoolean(HttpClientHelper.HASMORE);
                    //mPageCount = bundle.getLong(HttpClientHelper.COUNT);

                    try {
                        String json = (String) response.obj;
                        JSONObject obj = new JSONObject(json);
                        String goodsListJson = obj.getString("list");
                        mPageCount = obj.getLong("pagecount");
                        List<Goods> goodsList = Goods.arrayListBeanFromData(goodsListJson);
                        mRecyclerViewAdapter.addData(goodsList);
                        if ((mPageCount * Integer.parseInt(Constants.PAGESIZE)) > (Integer.parseInt(Constants.PAGESIZE) * mRecyclerViewAdapter.getPage())) {
                            mHasMore = true;
                        } else {
                            mHasMore = false;
                        }
                        mRecyclerViewAdapter.setHasMore(mHasMore);
                        mRecyclerViewAdapter.setPageCount(mPageCount);
                        mViewHolder.mSearchPageView.setText(mRecyclerViewAdapter.getPage() + "", mPageCount + "");
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
        //绑定设置遮罩的可见性的操作类
        mViewHolder.mSearchTab.setSearchMaskCallback(new SearchMaskCallback(this));
        //遮罩禁用滑动事件,并隐藏遮罩
        mViewHolder.mSearchMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(event.getRawY() > 0){
                            mViewHolder.mSearchTab.setSortLayoutVisibility(View.GONE);
                        }
                        break;
                }

                return true;
            }
        });
        mRecyclerViewAdapter = new RecyclerViewAdapter(this,this);
        mRecyclerViewAdapter.setHasMore(mHasMore);
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String goodsId) {
                IntentHelper.goodsDetail(SearchResultActivity.this, goodsId);
            }
        });
        mViewHolder.mPullLoadRecyclerView.setAdapter(mRecyclerViewAdapter);
        mViewHolder.mPullLoadRecyclerView.setOnScrollCallback(mScrollCallback);
        //设置搜索栏的高度
        postSearchTitleHeight();
        mViewHolder.mPullLoadRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(PullLoadRecyclerView.TYPE_LIST,
                StaggeredGridLayoutManager.VERTICAL));
        mViewHolder.mPullLoadRecyclerView.setItemDecoration(new RecyclerViewItemDecoration());
        mViewHolder.mPullLoadRecyclerView.setRecyclerViewTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mLoadState){
                    return true;
                }else{
                    return false;
                }

            }
        });
        initData();
    }


    @Override
    public void mixSort() {
        mOrderType = 1;
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void priceUp() {
        mOrderType = 3;
        mOrder = "ASC";
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void priceDown() {
        mOrderType = 3;
        mOrder = "DESC";
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void evalUp() {
        mOrderType = 4;
        mOrder = "ASC";
        initData();
    }

    @Override
    public void evalDown() {
        mOrderType = 4;
        mOrder = "DESC";
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void dateSort() {
        mOrderType = 5;
        mOrder = "DESC";
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void isOwnShop() {
        mOwnShop = mViewHolder.mSearchTab.isOwnShop();
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void isPromotion() {
        mIsPromotion = mViewHolder.mSearchTab.isPromotion();
        mRecyclerViewAdapter.clearData();
        initData();
    }

    @Override
    public void SalesSort() {
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
            mViewHolder.mSearchTab.setSortLayoutVisibility(View.GONE);
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
        mViewHolder.mSearchTitleLayout.post(new Runnable() {
            @Override
            public void run() {
                mViewHolder.mPullLoadRecyclerView.setPaddingTop(mViewHolder.mSearchTitleLayout.getHeight());
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
        public LinearLayout mSearchTitleLayout;
        public LinearLayout mSearchTitle;
        public SearchTab mSearchTab;
        public View  mSearchMask;
        public SearchHeaderView mSearchHeaderView;
        public ViewHolder(SearchResultActivity searchResultActivity) {
            mSearchResultActivity = searchResultActivity;
            initView();
        }

        public void initView(){
            mPullLoadRecyclerView = (PullLoadRecyclerView)mSearchResultActivity.findViewById(R.id.pull_search_list);
            mSearchNetErrorView = (SearchNetErrorView) mSearchResultActivity.findViewById(R.id.netErrorView);
            mSearchPageView = (SearchPageView) mSearchResultActivity.findViewById(R.id.search_page_view);
            mSearchNoResult = (SearchNoResult) mSearchResultActivity.findViewById(R.id.search_no_result_view);
            mSearchTitleLayout = (LinearLayout) mSearchResultActivity.findViewById(R.id.ll_search_title_layout);
            mSearchTitle = (LinearLayout) mSearchResultActivity.findViewById(R.id.ll_search_title);
            mSearchHeaderView = (SearchHeaderView) mSearchResultActivity.findViewById(R.id.searchHeader);
            mSearchTab = (SearchTab) mSearchResultActivity.findViewById(R.id.searchTab);
            mSearchMask = (View) mSearchResultActivity.findViewById(R.id.search_mask);
        }
    }

    public static void updateData(SearchResultActivity activity,int scrollState){
        if(scrollState != RecyclerView.SCROLL_STATE_IDLE && !activity.mLoadState && activity.mRecyclerViewAdapter.getPage()<activity.mPageCount) {
            activity.initData();
        }
    }

    /**
     * 遮罩的可见性
     */
    public class SearchMaskCallback{
        private SearchResultActivity mSearchResultActivity;

        public SearchMaskCallback(SearchResultActivity searchResultActivity) {
            mSearchResultActivity = searchResultActivity;
        }

        public void setSearchMakVisibility(int visibility){
           SearchResultActivity.getViewHolder(mSearchResultActivity).mSearchMask.setVisibility(visibility);
        }
    }

}
