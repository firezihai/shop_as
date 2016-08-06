package com.fengbeibei.shop.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.fragment.Base.GoodsBaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsGraphDetailFragment extends GoodsBaseFragment {
    private static final String TAG = "GoodsGraphFragment";
    @BindView(R.id.webView)
    WebView mWebView;
    private String mGoodsId;

    public static GoodsGraphDetailFragment newInstance(String goodsId){
        GoodsGraphDetailFragment goodsGraphDetailFragment = new GoodsGraphDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", goodsId);
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
                mDelayLoad = false;
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
    public void initView() {
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
        mDelayLoad = true;
      //  initData();
    }

    @Override
    public void setUpdate(String data) {
        mGoodsId = data;
        mDelayLoad = true;
    }

    @Override
    protected void lazyLoad() {
        if(!mVisible || !mDelayLoad){
            return;
        }
        initData();
    }
}
