package com.fengbeibei.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.SearchTabInterface;

/**
 * SearchTab
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-09 16:40
 */
public class SearchTab  extends FrameLayout implements View.OnClickListener{
    private Context mContext;
    private TextView mTvSearchSort;
    private LinearLayout mOpenSort;
    private TextView mTvMixSort;
    private ImageView mIvMixSort;
    private TextView mTvPriceUp;
    private ImageView mIvPriceUp;
    private TextView mTvPriceDown;
    private ImageView mIvPriceDown;
    private TextView mTvEvalUp;
    private ImageView mIvEvalUp;
    private TextView mTvEvalDown;
    private ImageView mIvEvalDown;
    private TextView mTvDate;
    private ImageView mIvDate;
    private LinearLayout mLlOwnShop;
    private ImageView mIvOwnShop;
    private TextView mTvOwnShop;
    private LinearLayout mLlPromotion;
    private ImageView mIvPromotion;
    private TextView mTvPromotion;
    private View mSearchSortMask;
    private SearchTabInterface mSearchTabInterface;
    private LinearLayout mLlSales;
    private TextView mTvSales;
    public SearchTab(Context context) {
        super(context);
        initView(context);
    }

    public SearchTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public SearchTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_search_sort, this);
        mTvSearchSort = (TextView) findViewById(R.id.tv_search_sort);
        mOpenSort = (LinearLayout) findViewById(R.id.ll_open_sort);
        mSearchSortMask = (View) findViewById(R.id.searchSortMask);
        mLlSales = (LinearLayout) findViewById(R.id.ll_sales);
        mTvSales = (TextView) findViewById(R.id.tv_sales);
        mIvPromotion = (ImageView) findViewById(R.id.iv_promotion);
        mLlPromotion = (LinearLayout) findViewById(R.id.ll_promotion);
        mTvPromotion = (TextView) findViewById(R.id.tv_promotion);
        mLlOwnShop = (LinearLayout) findViewById(R.id.ll_ownshop);
        mIvOwnShop = (ImageView) findViewById(R.id.iv_ownShop);
        mTvOwnShop = (TextView) findViewById(R.id.tv_ownshop);
        mTvMixSort = (TextView) findViewById(R.id.tv_mix_sort);
        mIvMixSort = (ImageView) findViewById(R.id.iv_mix_sort);
        mTvPriceUp = (TextView) findViewById(R.id.tv_price_up);
        mIvPriceUp = (ImageView) findViewById(R.id.iv_price_up);
        mTvPriceDown = (TextView) findViewById(R.id.tv_price_down);
        mIvPriceDown = (ImageView) findViewById(R.id.iv_price_down);
        mTvEvalUp = (TextView) findViewById(R.id.tv_eval_up);
        mIvEvalUp = (ImageView) findViewById(R.id.iv_eval_up);
        mTvEvalDown = (TextView) findViewById(R.id.tv_eval_down);
        mIvEvalDown = (ImageView) findViewById(R.id.iv_eval_down);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mIvDate = (ImageView) findViewById(R.id.iv_date);
        findViewById(R.id.ll_search_sort).setOnClickListener(this);
        findViewById(R.id.rl_mix_sort).setOnClickListener(this);
        findViewById(R.id.rl_price_up).setOnClickListener(this);
        findViewById(R.id.rl_price_down).setOnClickListener(this);
        findViewById(R.id.rl_eval_up).setOnClickListener(this);
        findViewById(R.id.rl_eval_down).setOnClickListener(this);
        findViewById(R.id.rl_date).setOnClickListener(this);
        findViewById(R.id.ll_sales).setOnClickListener(this);
        findViewById(R.id.ll_ownshop).setOnClickListener(this);
        findViewById(R.id.ll_promotion).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search_sort:
                    mOpenSort.setVisibility(View.VISIBLE);
                    mSearchSortMask.setVisibility(View.VISIBLE);
                 break;
            case R.id.rl_mix_sort:
                    mixSort();
                break;
            case R.id.rl_price_up:
                    priceUp();
                break;
            case R.id.rl_price_down:
                    priceDown();
                break;
            case R.id.rl_eval_up:
                    evalUp();
                break;
            case R.id.rl_eval_down:
                    evalDown();
                break;
            case R.id.rl_date:
                    dateSort();
                break;
            case R.id.ll_ownshop:
                ownShop();
                break;
            case R.id.ll_promotion:
                promotion();
                break;
            case R.id.ll_sales:
                salesSort();
                break;
        }
    }

    private void mixSort() {
        mTvSearchSort.setText("综合排序");
        setSelected(true, false, false, false, false, false,false);
        setTickVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
        mSearchTabInterface.mixSort();
    }

    private void priceUp() {
        mTvSearchSort.setText("价格升序");
        setSelected(false, true, false, false, false, false,false);
        setTickVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE);
        mSearchTabInterface.priceUp();
    }

    private void priceDown() {
        mTvSearchSort.setText("价格降序");
        setSelected(false, false, true, false, false, false,false);
        setTickVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE);
        mSearchTabInterface.priceDown();
    }

    private void evalUp() {
        mTvSearchSort.setText("价格降序");
        setSelected(false, false, false, true, false, false,false);
        setTickVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mSearchTabInterface.evalUp();
    }

    private void evalDown(){
        mTvSearchSort.setText("价格降序");

        setSelected(false,false,false,false,true,false,false);
        setTickVisibility(View.GONE,View.GONE ,View.GONE,View.GONE,View.VISIBLE,View.GONE);
        mSearchTabInterface.priceDown();
    }

    private void dateSort() {
        mTvSearchSort.setText("时间排序");
        setSelected(false,false,false,false,false,true,false);
        setTickVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE);
        mSearchTabInterface.dateSort();
    }

    private void promotion(){
        if(!mTvPromotion.isSelected()) {
            mIvPromotion.setImageResource(R.drawable.search_check_on);
            mTvPromotion.setSelected(true);
        }else{
            mIvPromotion.setImageResource(R.drawable.search_check_off);
            mTvPromotion.setSelected(false);
        }
        mSearchTabInterface.isPromotion();
    }
    private void ownShop(){
        if(!mTvOwnShop.isSelected()){
            mIvOwnShop.setImageResource(R.drawable.search_check_on);
            mTvOwnShop.setSelected(true);
        }else{
            mIvOwnShop.setImageResource(R.drawable.search_check_off);
            mTvOwnShop.setSelected(false);
        }
        mSearchTabInterface.isOwnShop();
    }

    private void salesSort(){
        if(!mTvSales.isSelected()) {
            setSelected(false, false, false, false, false, false, true);
            setTickVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
            mSearchTabInterface.SalesSort();
        }
    }
    public void setSearchTabListener(SearchTabInterface searchTabListener){
        mSearchTabInterface = searchTabListener;
    }
    public boolean isPromotion(){
        return mTvPromotion.isSelected();
    }
    public boolean isOwnShop(){
        return mTvOwnShop.isSelected();
    }


    public boolean isSortLayoutVisibility(){
        boolean bool = false;
        if(mOpenSort.getVisibility() == View.VISIBLE){
            bool = true;
        }
        return bool;
    }
    private void setSelected(boolean mix,boolean priceUp,boolean priceDown,boolean evalUp,boolean evalDown,boolean date,boolean sales){
        mTvMixSort.setSelected(mix);
        mTvPriceUp.setSelected(priceUp);
        mTvPriceDown.setSelected(priceDown);
        mTvEvalUp.setSelected(evalUp);
        mTvEvalDown.setSelected(evalDown);
        mTvDate.setSelected(date);
        if(sales){
            mTvSearchSort.setSelected(false);
        }else{
            mTvSearchSort.setSelected(true);
        }
        mTvSales.setSelected(sales);
    }

    private void setTickVisibility(int mix,int priceUp,int priceDown,int evalUp,int evalDown,int date){
        mIvMixSort.setVisibility(mix);
        mIvPriceUp.setVisibility(priceUp);
        mIvPriceDown.setVisibility(priceDown);
        mIvEvalUp.setVisibility(evalUp);
        mIvEvalDown.setVisibility(evalDown);
        mIvDate.setVisibility(date);
        mOpenSort.setVisibility(View.GONE);
        mSearchSortMask.setVisibility(View.GONE);
    }

    public void hideSortLayout(){
        if(mOpenSort.getVisibility() == View.VISIBLE){
            mOpenSort.setVisibility(View.GONE);
            mSearchSortMask.setVisibility(View.GONE);
        }
    }
}
