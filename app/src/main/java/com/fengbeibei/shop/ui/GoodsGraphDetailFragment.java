package com.fengbeibei.shop.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.ui.BaseFragment.GoodsBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsGraphDetailFragment extends GoodsBaseFragment {
    @BindView(R.id.webView)
    WebView mWebView;
    private String mGoodsId;

    public static GoodsGraphDetailFragment newInstance(String goodsId){
        GoodsGraphDetailFragment goodsGraphDetailFragment = new GoodsGraphDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId",goodsId);
        goodsGraphDetailFragment.setArguments(bundle);
        return goodsGraphDetailFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0" ;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.goods_graphdetail_fragment;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayoutId(), container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
    }

    @Override
    public void initData() {
        String requestUrl = Constants.GOODS_DETIAL_GRAPH_URL+"&goods_id="+ mGoodsId;
        mWebView.loadUrl(requestUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
                //	super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request, WebResourceError error) {
                // TODO Auto-generated method stub
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

        });
    }

    @Override
    public void initView(View view) {
        WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        if(Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        initData();
    }

    public void onUpdateUI(){
        initData();
    }

    @Override
    public void setGoodsId(String goodsId) {
       mGoodsId = goodsId;
    }


}
