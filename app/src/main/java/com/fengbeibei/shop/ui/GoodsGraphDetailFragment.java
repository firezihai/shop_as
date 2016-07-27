package com.fengbeibei.shop.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;

import org.apache.http.HttpStatus;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsGraphDetailFragment extends Fragment {
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_graphdetail_fragment, container, false);
        mWebView = (WebView) layout.findViewById(R.id.webView);
        String goods_id = getActivity().getIntent().getStringExtra("goods_id");
        String requestUrl = Constants.GOODS_DETIAL_GRAPH_URL+"&goods_id="+goods_id;

        mWebView.loadUrl(requestUrl);
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
        return layout;
    }


}
