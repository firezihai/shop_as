package com.fengbeibei.shop.activity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Spec;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.interf.GoodsFragmentListener;
import com.fengbeibei.shop.ui.BaseFragment.GoodsBaseFragment;
import com.fengbeibei.shop.ui.GoodsDetailFragment;
import com.fengbeibei.shop.ui.GoodsEvaluateFragment;
import com.fengbeibei.shop.ui.GoodsGraphDetailFragment;
import com.fengbeibei.shop.utils.DialogHelper;
import com.fengbeibei.shop.widget.FlowLayout;
import com.fengbeibei.shop.widget.indicator.TabPageIndicator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends FragmentActivity implements View.OnClickListener,GoodsDetailFragment.OnPopWindow,GoodsFragmentListener{

    /*控件*/
    @BindView(R.id.rootView) RelativeLayout mRootView;
    @BindView(R.id.back) ImageView mBackBtn;
    @BindView(R.id.goodsTab) TabPageIndicator mGoodsTab;
    @BindView(R.id.fragmentViewpager) ViewPager mFragmentViewPager;
    @BindView(R.id.storeBtn) TextView mStoreBtn;
    @BindView(R.id.cartBtn) TextView mCartBtn;
    @BindView(R.id.buyBtn) Button mBuyBtn;
    @BindView(R.id.addCartBtn) Button mAddCartBtn;
    /*goods_detail_common相关控件*/

    private ImageView mCloseBtn;
    private ImageView mGoodsImage;
    private TextView mGoodsShadow;
    private TextView mGoodsName;
    private TextView mGoodsPrice;
    private TextView mGoodsStorage;
    private LinearLayout mSpecList;
    private ImageView mAddBtn;
    private ImageView mMinusBtn;
    private EditText mBuyNum;
    private Button mBuyBtn2;
    private Button mAddCartBtn2;

    /* 商品规格控件 */
    private HashMap<String,View> mSpecViews = new HashMap<String,View>();
    private String mSpecListJson;
    /*当前商品的规格*/
    private ArrayList<String> mGoodsSpecSelected = new ArrayList<String>();
    private HashMap<String,String> mSelectedSpec = new HashMap<String,String>();
    /*图片缓存*/
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateListener = new AnimateFirstDisplayListener();

    private ArrayList<GoodsBaseFragment> mFragments = new ArrayList<>();
    private String[] mHeadTitle = {"商品","详情","评价"};
    /**
     * 是否可购买,库存不为零时可以购买和加入购物车
     */
    private boolean mIsBuy = true;
    /**
     * 是否可立即购买,当只有一种规格时可直接加入购物车和购买
     */
    private boolean mIsNowBuy = true;
    private PopupWindow mPop;
    private View mPopupWindowView;
    private String mGoodsId;
    private GoodsFragmentListener mFragmentListener;

    private GoodsDetailFragment mGoodsDetailFragment;
    private GoodsGraphDetailFragment mGoodsGraphDetailFragment;
    private GoodsEvaluateFragment mGoodsEvaluateFragment;

    private Dialog _waitDialog;
    private boolean _isVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        ButterKnife.bind(this);

        String goodsId = getIntent().getStringExtra("goods_id");
        mGoodsId = "10955";
        mGoodsDetailFragment = GoodsDetailFragment.newInstance(mGoodsId);
        mGoodsGraphDetailFragment = GoodsGraphDetailFragment.newInstance(mGoodsId);
        mGoodsEvaluateFragment = GoodsEvaluateFragment.newInstance(mGoodsId);
        mFragments.add(mGoodsDetailFragment);
        mFragments.add(mGoodsGraphDetailFragment);
        mFragments.add(mGoodsEvaluateFragment );
        GoodsFragmentViewPagerAdapter fragmentViewPagerAdapter = new GoodsFragmentViewPagerAdapter(getSupportFragmentManager());
        mFragmentViewPager.setAdapter(fragmentViewPagerAdapter);
        mFragmentViewPager.setOffscreenPageLimit(2);
        mGoodsTab.setViewPager(mFragmentViewPager);


        mPopupWindowView = getLayoutInflater().inflate(R.layout.goods_detail_common, null);
        mGoodsShadow = (TextView) mPopupWindowView.findViewById(R.id.goodsShadow);
        mGoodsImage = (ImageView) mPopupWindowView.findViewById(R.id.goodsImage);
        mGoodsName = (TextView) mPopupWindowView.findViewById(R.id.goodsName2);
        mGoodsPrice = (TextView) mPopupWindowView.findViewById(R.id.goodsPrice2);
        mGoodsStorage = (TextView) mPopupWindowView.findViewById(R.id.goodsStorage);
        mSpecList = (LinearLayout) mPopupWindowView.findViewById(R.id.specList);
        mAddBtn = (ImageView) mPopupWindowView.findViewById(R.id.addBtn);
        mMinusBtn = (ImageView) mPopupWindowView.findViewById(R.id.minusBtn);
        mBuyNum = (EditText) mPopupWindowView.findViewById(R.id.buyNum);
              mPop = new PopupWindow(mPopupWindowView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        mPop.setAnimationStyle(R.style.animationFade);
        mGoodsShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePopWindow();
            }
        });

        mStoreBtn.setOnClickListener(this);
        mCartBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mAddCartBtn.setOnClickListener(this);
        mBuyBtn.setOnClickListener(this);
        _isVisible = true;
        showWaitDialog();

    }


    public void initData(String goodsDetail) {
        hideWaitDialog();
        try{
            JSONObject goodsObj = new JSONObject(goodsDetail);
            String goods_info = goodsObj.getString("goods_info");
            mSpecListJson = goodsObj.getString("spec_list");
            mSpecList.removeAllViews();
            mSpecViews.clear();
            mGoodsSpecSelected.clear();
            JSONObject obj = new JSONObject(goods_info);
            if(obj.length()>0){


                String goodsName = obj.optString("goods_name");
                int goodsStorage = Integer.parseInt(obj.optString("goods_storage"));
                mGoodsName.setText(goodsName);
                mGoodsPrice.setText("￥"+obj.optString("goods_price"));
                mGoodsStorage.setText(obj.optString("goods_storage"));
                String goodsSpec = obj.optString("goods_spec");

                if(!goodsSpec.equals("") && !goodsSpec.equals("null")){
                    JSONObject objGoodsSpec = new JSONObject(goodsSpec);
                    Iterator<?> itGoodsSpec = objGoodsSpec.keys();

                    while(itGoodsSpec.hasNext()){
                        final String goodsSpecId = itGoodsSpec.next().toString();
                       // String goodsSpecName = objGoodsSpec.getString(goodsSpecId);
                        mGoodsSpecSelected.add(goodsSpecId);
                    }
                }
                //  mImageLoader.displayImage(goodsImageArr[0], mGoodsImage, mOptions, mAnimateListener);

                if(goodsStorage <= 0){
                    mBuyBtn.setBackgroundResource(R.color.pub_color_one);
                    mBuyBtn.setEnabled(false);
                    mAddCartBtn.setBackgroundResource(R.color.pub_color_one);
                    mAddCartBtn.setEnabled(false);
                    mIsBuy = false;
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

                        LinearLayout specListView = (LinearLayout) getLayoutInflater().inflate(R.layout.goods_spec, null);
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
                            if (mGoodsSpecSelected.contains(specValueId)) {
                                mSelectedSpec.put(specId,specValueId);
                                specValueItemView.setChecked(true);
                            }

                            mSpecViews.put(specValueId, specValueItemView);

                            specValueItemView.setOnClickListener(new RegSpecOnClickListener(specId,specValueId));
                            specValueView.addView(specValueItemView);

                        }
                        if(list.size()>2){
                            mIsNowBuy = false;
                        }
                        mSpecList.addView(specListView);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





        mGoodsShadow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
    }

    public class GoodsFragmentViewPagerAdapter extends FragmentPagerAdapter {
        public GoodsFragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }



        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mHeadTitle[position % mHeadTitle.length];
        }
    }

    public void changePopWindow(){
        if(mPop.isShowing()){
            mPop.dismiss();
        }else{
            mPop.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void setOnPopWindow() {
        changePopWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.storeBtn:
               // IntentHelper.home(GoodsDetailActivity.this);
                break;
            case R.id.cartBtn:
                IntentHelper.home(GoodsDetailActivity.this,2);
                finish();
                break;
            case R.id.addCartBtn:
                if(mAddCartBtn.isEnabled()){
                    if(mIsNowBuy){

                    }else {
                        changePopWindow();
                    }
                }
                break;
            case R.id.buyBtn:
                if(mBuyBtn.isEnabled()){
                    if(mIsNowBuy){

                    }else{
                        changePopWindow();
                    }
                }
                break;
            case R.id.back:
                if(mFragmentViewPager.getCurrentItem() > 0){
                    mFragmentViewPager.setCurrentItem(0);
                }else{
                    finish();
                }
                break;
        }
    }



    public void addCart(String goods_id){

    }

    class RegSpecOnClickListener implements View.OnClickListener{
        private String specId;
        private String curSpecValueId;
        private String curKey = "";
        public RegSpecOnClickListener(String specId,String specValueId) {
            this.specId = specId;
            this.curSpecValueId = specValueId;
        }

        @Override
        public void onClick(View v) {
            Iterator iterator = mSpecViews.keySet().iterator();
            mSelectedSpec.put(specId, curSpecValueId);
            int[] curKeySort = new int[mSelectedSpec.size()];
            while(iterator.hasNext()) {
                String  specValueId = iterator.next().toString();
                CheckBox checkBox = (CheckBox) mSpecViews.get(specValueId);
                checkBox.setChecked(false);
                Iterator it = mSelectedSpec.keySet().iterator();
                int i=0;
                while (it.hasNext()){
                    String selectedSpecId = it.next().toString();
                    String selectedSpecValueId = mSelectedSpec.get(selectedSpecId);
                    if(selectedSpecValueId.equals(specValueId)){
                        checkBox.setChecked(true);
                        curKeySort[i] = Integer.parseInt(specValueId);
                        //curKey += "|"+specValueId;
                    }
                    i++;
                }
            }
            Arrays.sort(curKeySort);
            for(int k =0;k<curKeySort.length;k++){
                curKey += "|"+curKeySort[k];
            }
            curKey = curKey.replaceFirst("\\|","");
            System.out.println(mSpecListJson);
            try{
                JSONObject obj = new JSONObject(mSpecListJson);

                String goodsId = obj.getString(curKey);
                mGoodsId = goodsId;
                updateFragment();
             //   mFragmentListener.(goodsId);
                showWaitDialog();
                mGoodsDetailFragment.upData();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }



    public void updateFragment(){
        mGoodsDetailFragment.setUpdate(mGoodsId);
        mGoodsGraphDetailFragment.setUpdate(mGoodsId);
        mGoodsEvaluateFragment.setUpdate(mGoodsId);
    }

    @Override
    public void onUpdateUI(String data) {
        showWaitDialog();
        initData(data);
    }


    private Dialog showWaitDialog(){
        if(_isVisible){
            if(_waitDialog == null){
                _waitDialog = DialogHelper.getDialog(this,"",R.layout.view_dialog_loading,R.style.Dialog);
            }
            if(_waitDialog != null){
             //   _waitDialog.setMessage("加载中");
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    private void hideWaitDialog(){
        if(_isVisible && _waitDialog != null){
            try{
                _waitDialog.dismiss();
                _waitDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
