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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search_sort:
                    setSortLayoutVisibility(View.VISIBLE);
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
        }
    }

    private void setSortLayoutVisibility(int visibility){
        mOpenSort.setVisibility(visibility);
    }

    private void mixSort(){
        mTvSearchSort.setText("综合排序");
        setSortLayoutVisibility(View.GONE);
        setSelected(true, false, false, false, false, false);
        setTickVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
    }

    private void priceUp(){
        mTvSearchSort.setText("价格升序");
        setSortLayoutVisibility(View.GONE);
        setSelected(false,true,false,false,false,false);
        setTickVisibility(View.GONE,View.VISIBLE,View.GONE,View.GONE,View.GONE,View.GONE);
    }

    private void priceDown(){
        mTvSearchSort.setText("价格降序");
        setSortLayoutVisibility(View.GONE);
        setSelected(false,false,true,false,false,false);
        setTickVisibility(View.GONE,View.GONE ,View.VISIBLE,View.GONE,View.GONE,View.GONE);
    }

    private void evalUp(){
        mTvSearchSort.setText("价格降序");
        setSortLayoutVisibility(View.GONE);
        setSelected(false,false,false,true,false,false);
        setTickVisibility(View.GONE,View.GONE ,View.GONE,View.VISIBLE,View.GONE,View.GONE);
    }

    private void evalDown(){
        mTvSearchSort.setText("价格降序");
        setSortLayoutVisibility(View.GONE);
        setSelected(false,false,false,false,true,false);
        setTickVisibility(View.GONE,View.GONE ,View.GONE,View.GONE,View.VISIBLE,View.GONE);
    }

    private void dateSort(){
        mTvSearchSort.setText("时间排序");
        setSortLayoutVisibility(View.GONE);
        setSelected(false,false,false,false,false,true);
        setTickVisibility(View.GONE,View.GONE ,View.GONE,View.GONE,View.GONE,View.VISIBLE);
    }
    private void setSelected(boolean mix,boolean priceUp,boolean priceDown,boolean evalUp,boolean evalDown,boolean date){
        mTvMixSort.setSelected(mix);
        mTvPriceUp.setSelected(priceUp);
        mTvPriceDown.setSelected(priceDown);
        mTvEvalUp.setSelected(evalUp);
        mTvEvalDown.setSelected(evalDown);
        mTvDate.setSelected(date);
    }

    private void setTickVisibility(int mix,int priceUp,int priceDown,int evalUp,int evalDown,int date){
        mIvMixSort.setVisibility(mix);
        mIvPriceUp.setVisibility(priceUp);
        mIvPriceDown.setVisibility(priceDown);
        mIvEvalUp.setVisibility(evalUp);
        mIvEvalDown.setVisibility(evalDown);
        mIvDate.setVisibility(date);
    }
}
