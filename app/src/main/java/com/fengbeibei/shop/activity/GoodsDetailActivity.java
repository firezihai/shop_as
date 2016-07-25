package com.fengbeibei.shop.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.GoodsCommendAdapter;
import com.fengbeibei.shop.bean.GoodsCommend;
import com.fengbeibei.shop.bean.GoodsStoreInfo;
import com.fengbeibei.shop.bean.Order;
import com.fengbeibei.shop.bean.Spec;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.utils.ScreenUtil;
import com.fengbeibei.shop.widget.FlowLayout;
import com.fengbeibei.shop.widget.MyGridView;
import com.fengbeibei.shop.widget.indicator.CirclePageIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends Activity{
	
	/*控件*/
	@BindView(R.id.rootView) RelativeLayout mRootView;
	@BindView(R.id.back) ImageView mBackBtn;
	@BindView(R.id.tabGoods) TextView mTabGoods;
	@BindView(R.id.tabDetail) TextView mTabDetail;
	@BindView(R.id.tabEval) TextView mTabEval;
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
	@BindView(R.id.homeBtn) TextView mHomeBtn;
	@BindView(R.id.cartBtn) TextView mCartBtn;
	@BindView(R.id.buyBtn) Button mBuyBtn;
	@BindView(R.id.addCartBtn) Button mAddCartBtn;
	@BindView(R.id.goodsCommendGridView) MyGridView mGoodsCommend;
	/*goods_detail_store_info相关控件*/
	@BindView(R.id.storeName) TextView mStoreName;
	@BindView(R.id.descCredit) TextView mDescCredit;
	@BindView(R.id.serviceCredit) TextView mServiceCredit;
	@BindView(R.id.shipCredit) TextView mShipCredit;
	@BindView(R.id.descCreditPercent) TextView mDescCreditPercent;
	@BindView(R.id.serviceCreditPercent) TextView mServiceCreditPercent;
	@BindView(R.id.shipCreditPercent) TextView mShipCreditPercent;
	/*goods_detail_common相关控件*/
	private View mPopupWindowView;
	private ImageView mCloseBtn;
	private ImageView mGoodsImage;
	private TextView mGoodsShadow;
	private TextView mGoodsName2;
	private TextView mGoodsPrice2;
	private TextView mGoodsStorage;
	private LinearLayout mSpecList;
	private ImageView mAddBtn;
	private ImageView mMinusBtn;
	private EditText mBuyNum;
	private Button mBuyBtn2;
	private Button mAddCartBtn2;



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

	private PopupWindow mPop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_detail);
		ButterKnife.bind(this);

		mScreenWidth = ScreenUtil.getScreenWidth(GoodsDetailActivity.this);

		mViewPagerWrapper.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));


		mPopupWindowView = getLayoutInflater().inflate(R.layout.goods_detail_common, null);

		mGoodsShadow = (TextView) mPopupWindowView.findViewById(R.id.goodsShadow);
		mGoodsImage = (ImageView) mPopupWindowView.findViewById(R.id.goodsImage);
		mGoodsName2 = (TextView) mPopupWindowView.findViewById(R.id.goodsName2);
		mGoodsPrice2 = (TextView) mPopupWindowView.findViewById(R.id.goodsPrice2);
		mGoodsStorage = (TextView) mPopupWindowView.findViewById(R.id.goodsStorage);
		mSpecList = (LinearLayout) mPopupWindowView.findViewById(R.id.specList);
		mAddBtn = (ImageView) mPopupWindowView.findViewById(R.id.addBtn);
		mMinusBtn = (ImageView) mPopupWindowView.findViewById(R.id.minusBtn);
		mBuyNum = (EditText) mPopupWindowView.findViewById(R.id.buyNum);

		mPop = new PopupWindow(mPopupWindowView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPop.setAnimationStyle(R.style.animationFade);



		Intent intent = getIntent();
		String  goods_id = intent.getStringExtra("goods_id");
		String request_url = Constants.GOODS_DETAIL_URL+"&goods_id="+goods_id;
		HttpClientHelper.asynGet(request_url, new CallBack(){

			@Override
			public void onFinish(Message response) {
				// TODO Auto-generated method stub
				if(response.what == HttpStatus.SC_OK){
					try{
						String json = (String)response.obj;

						JSONObject obj = new JSONObject(json);
						String goods_info = obj.getString("goods_info");
						String goods_commend_list = obj.getString("goods_commend_list");
						String goods_image = obj.getString("goods_image");
						String gift_array = obj.getString("gift_array");
						String mansong_info = obj.getString("mansong_info");
						String spec_image = obj.getString("spec_image");
						String spec_list = obj.getString("spec_list");
						String store_info= obj.getString("store_info");
						
						goodsInfo(goods_info);
						goodsImage(goods_image);
						goodsStoreInfo(store_info);
						goodsCommend(goods_commend_list);

					} catch (JSONException e){
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
				mPop.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
			}
		});
		mGoodsShadow.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				mPop.dismiss();
			}
		});
	}

	private void goodsInfo(String goodsInfo){

		try{
			JSONObject obj = new JSONObject(goodsInfo);
			if(obj.length()>0){
				String goodsName = obj.optString("goods_name");
				int goodsStorage = Integer.parseInt(obj.optString("goods_storage"));

				mGoodsName.setText(goodsName);
				mGoodsName2.setText(goodsName);
				mGoodsPrice.setText("￥"+obj.optString("goods_price"));
				mGoodsPrice2.setText("￥"+obj.optString("goods_price"));
				mGoodsMarketPrice.setText("￥"+obj.optString("goods_marketprice"));
				mSaleCount.setText(obj.optString("goods_salenum"));
				mGoodsSerial.setText(obj.optString("goods_serial"));
				mGoodsStorage.setText(goodsStorage+"");
				if(goodsStorage <= 0){
					mBuyBtn.setBackgroundResource(R.color.pub_color_one);
					mBuyBtn.setEnabled(false);
					mAddCartBtn.setBackgroundResource(R.color.pub_color_one);
					mAddCartBtn.setEnabled(false);
				}

				String goodsSpecStr = "";
				String goodsSpec = obj.optString("goods_spec");

				ArrayList<String> goodsSpecArr = new ArrayList<String>();
				if(!goodsSpec.equals("") && !goodsSpec.equals("null")){
					JSONObject objGoodsSpec = new JSONObject(goodsSpec);
					Iterator<?> itGoodsSpec = objGoodsSpec.keys();
					int i=0;
					while(itGoodsSpec.hasNext()){
						final String goodsSpecId = itGoodsSpec.next().toString();
						String goodsSpecName = objGoodsSpec.getString(goodsSpecId);
						goodsSpecArr.add(goodsSpecId);
						goodsSpecStr +=" "+goodsSpecName;
						i++;
					}
					mGoodsSpecInfo.setText(goodsSpecStr);
				}




				String goodsSpecName = obj.optString("spec_name");
				String goodsSpecValue = obj.optString("spec_value");
				if(!goodsSpecName.equals("") && !goodsSpecName.equals("null") && !goodsSpecValue.equals("") && !goodsSpecValue.equals("null")){
					JSONObject objSpecName = new JSONObject(goodsSpecName);
					JSONObject objSpecValue = new JSONObject(goodsSpecValue);
					Iterator<?> itSpecName = objSpecName.keys();
					while (itSpecName.hasNext()){
						ArrayList<Spec> list = new ArrayList<Spec>();
						final String specId = itSpecName.next().toString();
						String specName = objSpecName.getString(specId);
						String specValue = objSpecValue.getString(specId);
						JSONObject jsonObj = new JSONObject(specValue);

						LinearLayout specListView = (LinearLayout)getLayoutInflater().inflate(R.layout.goods_spec, null);
						TextView specNameView = (TextView)specListView.findViewById(R.id.specName);
						specNameView.setText(specName);

                        FlowLayout specValueView = (FlowLayout) specListView.findViewById(R.id.specValueView);
                        specValueView.removeAllViews();
						Iterator<?> iterator = jsonObj.keys();
						while (iterator.hasNext()){
							final String specValueId = iterator.next().toString();
							String specValueName = jsonObj.getString(specValueId);
							Spec spec = new Spec(specValueId,specValueName);
							list.add(spec);


                            CheckBox specValueItemView = (CheckBox) getLayoutInflater().inflate(R.layout.goods_spec_item,null);
                            specValueItemView.setText(specValueName);
                            if (goodsSpecArr.contains(specValueId)) {
                                specValueItemView.setChecked(true);
                            }
                            specValueView.addView(specValueItemView);
							/*checkBox.setText(specValueName);
							if (goodsSpecArr.contains(specValueId)) {
								checkBox.setBackgroundResource(R.color.red_btn);
							}
							specValueView.addView(specValueItemView);*/
						}

						mSpecList.addView(specListView);
					}




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

		mGoodsCommend.setAdapter(new GoodsCommendAdapter(goodsCommends, MyApplication.getContext()));
	}
	private void goodsImage(String goodsImage){

		if(goodsImage.equals("") && goodsImage.equals("null")){
			return;
		}
		String[] goodsImageArr = goodsImage.split(",");
		if(goodsImageArr.length<=0){
			return ;
		}

		mImageLoader.displayImage(goodsImageArr[0], mGoodsImage, mOptions, mAnimateListener);
		mImageViewPager.removeAllViews();
		goodsImageData.clear();
		for(int i=0 ;i<goodsImageArr.length;i++){
			String url = goodsImageArr[i];
			ImageView imageView = new ImageView(GoodsDetailActivity.this);
			imageView.setScaleType(ScaleType.FIT_XY);
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
		mImageViewPager.addOnPageChangeListener(new OnPageChangeListener(){

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
	
	private class GoodsImageAdapter extends PagerAdapter{
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
