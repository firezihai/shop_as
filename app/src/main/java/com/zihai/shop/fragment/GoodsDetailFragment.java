package com.zihai.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.activity.PhotoActivity;
import com.zihai.shop.adapter.GoodsCommendAdapter;
import com.zihai.shop.adapter.GoodsImagePagerAdapter;
import com.zihai.shop.bean.GoodsCommend;
import com.zihai.shop.bean.GoodsStoreInfo;
import com.zihai.shop.common.AnimateFirstDisplayListener;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.SystemHelper;
import com.zihai.shop.fragment.Base.GoodsBaseFragment;
import com.zihai.shop.interf.GoodsFragmentListener;
import com.zihai.shop.utils.ScreenUtil;
import com.zihai.shop.widget.MyGridView;
import com.zihai.shop.widget.indicator.CirclePageIndicator;
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

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsDetailFragment extends GoodsBaseFragment {
    private String TAG = "GoodsDetailFragment";
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

    private String mGoodsId;
    private GoodsFragmentListener mFragmentListener;
    public interface OnPopWindow{
        void setOnPopWindow();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.goods_detail_fragment;
    }

    public static GoodsDetailFragment newInstance( String goodsId){
       GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
        Bundle args = new Bundle();
        args.putString("goodsId", goodsId);

        goodsDetailFragment.setArguments(args);

        return goodsDetailFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0" ;
    }

    @Override
    public void initView() {
        mLoadingLayout = R.layout.view_dialog_loading;
        mLoadingStyle = R.style.Dialog;
        mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
        mViewPagerWrapper.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));

        initData();
        mGoodsSpecWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof OnPopWindow) {
                    ((OnPopWindow) getActivity()).setOnPopWindow();
                    ;
                }
            }
        });
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
        mShipCredit.setText(goodsStoreInfo.getStoreCredit().getStoreDeliverycredit().getCredit() + "");
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
    private void goodsImage(final String goodsImage) {

        if (goodsImage.equals("") && goodsImage.equals("null")) {
            return;
        }
        final String[] goodsImageArr = goodsImage.split(",");
        if (goodsImageArr.length <= 0) {
            return;
        }

        mImageViewPager.removeAllViews();
        goodsImageData.clear();
        for (int i = 0; i < goodsImageArr.length; i++) {
            final int position = i;
            String url = goodsImageArr[i];
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));
            mImageLoader.displayImage(url, imageView, mOptions, mAnimateListener);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PhotoActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("images", goodsImageArr);
                    getActivity().startActivity(intent);
                }
            });
            goodsImageData.add(imageView);
        }


        GoodsImagePagerAdapter adapter = new GoodsImagePagerAdapter(goodsImageData);
        mImageViewPager.setAdapter(adapter);

        mPointViewPager.setViewPager(mImageViewPager);
        mPointViewPager.setCurrentItem(0);


    }

    @Override
    public void initData() {
        showLoadingDialog("");
        String request_url = Constants.GOODS_DETAIL_URL+"&goods_id=" + mGoodsId;
        HttpClientHelper.asynGet(request_url, new HttpClientHelper.CallBack() {

            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
                ;
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

                        mFragmentListener.onUpdateUI(json);
                        //    GoodsDetailActivity goodsDetailActivity = (GoodsDetailActivity) getActivity();
                        //    goodsDetailActivity.setCallBack(json);


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
    }





    @Override
    public void onAttach(Context context) {
        if(context instanceof GoodsFragmentListener){
            mFragmentListener = (GoodsFragmentListener) context;
        }
        super.onAttach(context);
    }


    @Override
    public void setUpdate(String data) {
        mGoodsId = data;
    }

    @Override
    protected void lazyLoad() {

    }

    public void upData(){
        initData();
    }
}
