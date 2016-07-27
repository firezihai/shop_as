package com.fengbeibei.shop.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.GoodsDetailActivity;
import com.fengbeibei.shop.adapter.GoodsCommendAdapter;
import com.fengbeibei.shop.bean.GoodsCommend;
import com.fengbeibei.shop.bean.GoodsStoreInfo;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.utils.ScreenUtil;
import com.fengbeibei.shop.widget.MyGridView;
import com.fengbeibei.shop.widget.indicator.CirclePageIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsDetailFragment extends Fragment{

    @BindView(R.id.viewPagerWrapper) RelativeLayout mViewPagerWrapper;
    @BindView(R.id.goodsImageViewPager) ViewPager mImageViewPager;
    @BindView(R.id.goodsImagePoint) CirclePageIndicator mPointViewPager;
    @BindView(R.id.goodsName) TextView mGoodsName;
    @BindView(R.id.goodsPrice) TextView mGoodsPrice;
    @BindView(R.id.goodsMarketPrice) TextView mGoodsMarketPrice;
    @BindView(R.id.saleCount) TextView mSaleCount;
    @BindView(R.id.goodsSerial) TextView mGoodsSerial;
    @BindView(R.id.goodsSpecWrapper) LinearLayout mGoodsSpecWrapper;
    @BindView(R.id.goodsSpecInfo) TextView mGoodsSpecInfo;
    @BindView(R.id.goodsCommendGridView) MyGridView mGoodsCommend;
    @BindView(R.id.storeName) TextView mStoreName;
    @BindView(R.id.descCredit) TextView mDescCredit;
    @BindView(R.id.serviceCredit) TextView mServiceCredit;
    @BindView(R.id.shipCredit) TextView mShipCredit;
    @BindView(R.id.descCreditPercent) TextView mDescCreditPercent;
    @BindView(R.id.serviceCreditPercent) TextView mServiceCreditPercent;
    @BindView(R.id.shipCreditPercent) TextView mShipCreditPercent;



    /*商品轮播相关*/
    private ArrayList<ImageView> goodsImageData = new ArrayList<ImageView>();
    private boolean mIsMoving = false;
    private boolean mIsScroll = false;
    private int mCurrentIndex = 0;
    private Handler mHandler;
    private final int SHOW_NEXT = 0x0011;
    private int mScreenWidth;



    /*图片缓存*/
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateListener = new AnimateFirstDisplayListener();



    public interface OnPopWindow{
        void setOnPopWindow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_detail_fragment,container,false);
        ButterKnife.bind(this, layout);

        mScreenWidth = ScreenUtil.getScreenWidth(getActivity());

        mViewPagerWrapper.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));


        String goods_id = getActivity().getIntent().getStringExtra("goods_id");
        String request_url = Constants.GOODS_DETAIL_URL+"&goods_id=" + goods_id;
        HttpClientHelper.asynGet(request_url, new HttpClientHelper.CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                if (response.what == HttpStatus.SC_OK) {
                    try {
                        String json = (String) response.obj;

                        JSONObject obj = new JSONObject(json);
                        String goods_info = obj.getString("goods_info");
                        String goods_commend_list = obj.getString("goods_commend_list");
                        String goods_image = obj.getString("goods_image");
                        String gift_array = obj.getString("gift_array");
                        String mansong_info = obj.getString("mansong_info");
                        String spec_image = obj.getString("spec_image");
                        String spec_list = obj.getString("spec_list");
                        String store_info = obj.getString("store_info");

                        goodsInfo(goods_info);
                        goodsImage(goods_image);
                        goodsStoreInfo(store_info);
                        goodsCommend(goods_commend_list);

                        GoodsDetailActivity goodsDetailActivity = (GoodsDetailActivity)getActivity();
                        goodsDetailActivity.setCallBack(json);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                // TODO Auto-generated method stub

            }

        });

        mGoodsSpecWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof OnPopWindow){
                    ((OnPopWindow) getActivity()).setOnPopWindow();;
                }
            }
        });
        return layout;
    }
    private void goodsInfo(String goodsInfo){

        try{
            JSONObject obj = new JSONObject(goodsInfo);
            if(obj.length()>0){

                String goodsName = obj.optString("goods_name");


                mGoodsName.setText(goodsName);
                mGoodsPrice.setText("￥"+obj.optString("goods_price"));
                mGoodsMarketPrice.setText("￥"+obj.optString("goods_marketprice"));
                mSaleCount.setText(obj.optString("goods_salenum"));
                mGoodsSerial.setText(obj.optString("goods_serial"));
                String goodsSpecStr = "";
                String goodsSpec = obj.optString("goods_spec");

                if(!goodsSpec.equals("") && !goodsSpec.equals("null")){
                    JSONObject objGoodsSpec = new JSONObject(goodsSpec);
                    Iterator<?> itGoodsSpec = objGoodsSpec.keys();
                    int i=0;
                    while(itGoodsSpec.hasNext()){
                        final String goodsSpecId = itGoodsSpec.next().toString();
                        String goodsSpecName = objGoodsSpec.getString(goodsSpecId);
                        goodsSpecStr +=" "+goodsSpecName;
                        i++;
                    }
                    mGoodsSpecInfo.setText(goodsSpecStr);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void goodsStoreInfo(String storeInfo){
        Gson gson = new Gson();
        GoodsStoreInfo goodsStoreInfo = gson.fromJson(storeInfo,GoodsStoreInfo.class);
        mStoreName.setText(goodsStoreInfo.getStoreName());
        mDescCredit.setText(goodsStoreInfo.getStoreCredit().getStoreDesccredit().getCredit()+"");
        mDescCreditPercent.setText(goodsStoreInfo.getStoreCredit().getStoreDesccredit().getPercentText());
        mServiceCredit.setText(goodsStoreInfo.getStoreCredit().getStoreServicecredit().getCredit()+"");
        mServiceCreditPercent.setText(goodsStoreInfo.getStoreCredit().getStoreServicecredit().getPercentText());
        mShipCredit.setText(goodsStoreInfo.getStoreCredit().getStoreDeliverycredit().getCredit()+"");
        mShipCreditPercent.setText(goodsStoreInfo.getStoreCredit().getStoreDeliverycredit().getPercentText());
        ;
    }

    private void goodsCommend(String goodsCommendStr){
        Gson gson = new Gson();
        ArrayList<GoodsCommend> goodsCommends= gson.fromJson(goodsCommendStr,
                new TypeToken<ArrayList<GoodsCommend>>() {
                }.getType());
        mGoodsCommend.setFocusable(false);
        mGoodsCommend.setAdapter(new GoodsCommendAdapter(goodsCommends, getActivity()));

    }
    private void goodsImage(String goodsImage){

        if(goodsImage.equals("") && goodsImage.equals("null")){
            return;
        }
        String[] goodsImageArr = goodsImage.split(",");
        if(goodsImageArr.length<=0){
            return ;
        }

        mImageViewPager.removeAllViews();
        goodsImageData.clear();
        for(int i=0 ;i<goodsImageArr.length;i++){
            String url = goodsImageArr[i];
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));
            mImageLoader.displayImage(url, imageView, mOptions, mAnimateListener);
            goodsImageData.add(imageView);
        }
        //	mHandler.sendEmptyMessageAtTime(SHOW_NEXT,3000);


        GoodsImageAdapter adapter = new GoodsImageAdapter(goodsImageData);
        mImageViewPager.setAdapter(adapter);

        mPointViewPager.setViewPager(mImageViewPager);
        mPointViewPager.setCurrentItem(0);
        mImageViewPager.setOnTouchListener(new RegOnTouchListener());
        mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
                //ViewPager.SCROLL_STATE_IDLE　暂停状态
                mIsMoving = state != ViewPager.SCROLL_STATE_IDLE;
                mIsScroll = state != ViewPager.SCROLL_STATE_IDLE;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub
                mIsMoving = position != mCurrentIndex;
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                mIsMoving = false;
                mCurrentIndex = position;
                mIsScroll = false;
            }

        });

	/*	mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
					case SHOW_NEXT:
						showNext();
						mImageViewPager.setCurrentItem(mCurrentIndex);
						mHandler.sendEmptyMessageDelayed(SHOW_NEXT, 3000);
						break;
					default:
						break;
				}
				super.handleMessage(msg);
			}

		};
        if(goodsImage.length>0){
            mHandler.sendEmptyMessageDelayed(SHOW_NEXT,3000);
        }*/
    }

	/*private void showNext(){
		if(!mIsMoving && !mIsScroll){
			mCurrentIndex = (mCurrentIndex +1) % goodsImageData.size();
		}
	}*/

    private class GoodsImageAdapter extends PagerAdapter {
        private ArrayList<ImageView> mData;


        /**
         * @param data
         */
        public GoodsImageAdapter(ArrayList<ImageView> data) {
            super();
            this.mData = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return this.mData.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return (arg0 == arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            ((ViewPager) container).addView(mData.get(position));
            return mData.get(position);
        }

    }
    private class RegOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch(event.getAction()){
                case MotionEvent.ACTION_UP:
                    mIsMoving = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mIsMoving = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    mIsMoving = true;
                    break;
            }
            return false;
        }

    }

}
