package com.fengbeibei.shop.ui;

import android.content.Context;
import android.content.Intent;
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
import com.fengbeibei.shop.activity.PhotoActivity;
import com.fengbeibei.shop.adapter.GoodsCommendAdapter;
import com.fengbeibei.shop.adapter.GoodsImagePagerAdapter;
import com.fengbeibei.shop.bean.GoodsCommend;
import com.fengbeibei.shop.bean.GoodsStoreInfo;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.interf.FragmentListener;
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
public class GoodsDetailFragment extends Fragment implements FragmentListener{

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
    private FragmentListener mFragmentListener;
    public interface OnPopWindow{
        void setOnPopWindow();
    }


   public static GoodsDetailFragment newInstance( String goodsId){
       GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
        Bundle args = new Bundle();
        args.putString("goodsId",goodsId);

        goodsDetailFragment.setArguments(args);

        return goodsDetailFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mGoodsId = bundle.getString("goodsId");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_detail_fragment,container,false);
        ButterKnife.bind(this, layout);

        mScreenWidth = ScreenUtil.getScreenWidth(getActivity());

        mViewPagerWrapper.setLayoutParams(new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth));
        initData(mGoodsId);
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

    public void initData(String goodsId){

        String request_url = Constants.GOODS_DETAIL_URL+"&goods_id=" + goodsId;
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

                        mFragmentListener.OnUpdateUI(json);
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
    public void OnUpdateUI(String data) {
        System.out.println("GoodsFragment onUpdateUI "+data);
        initData(data);
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof FragmentListener){
            mFragmentListener = (FragmentListener) context;
        }
        super.onAttach(context);
    }
}
