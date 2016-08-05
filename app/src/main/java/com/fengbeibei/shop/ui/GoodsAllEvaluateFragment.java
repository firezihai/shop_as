package com.fengbeibei.shop.ui;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.GoodsEvalAdapter;
import com.fengbeibei.shop.bean.GoodsEval;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.ui.BaseFragment.ViewPagerFragment;
import com.fengbeibei.shop.widget.MyListView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2016-08-01.
 */
public class GoodsAllEvaluateFragment extends ViewPagerFragment implements AbsListView.OnScrollListener{
    private static final String TAG = "GoodsAllEValFragment";
    private String mGoodsId;
    private int mPage =1;
    private LinearLayout mFooterView;
    private List<GoodsEval> mGoodsEval;
    private GoodsEvalAdapter mGoodsEvalAdapter;
    private Boolean mHasmore = true;
    private long mPageCount = 1;
    private View mRootView;
    @BindView(R.id.listView)
    MyListView mListView;

    public static GoodsAllEvaluateFragment newInstance(String goodsId){
        GoodsAllEvaluateFragment goodsAllEvaluateFragment = new GoodsAllEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", goodsId);
        goodsAllEvaluateFragment.setArguments(bundle);
        return goodsAllEvaluateFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0" ;
    }

  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null) {
            mDelayLoad = true;
            mRootView = inflater.inflate(R.layout.goods_evaluate_all_fragment, container, false);
            ButterKnife.bind(this, mRootView);

            // initData();
        }
        return mRootView;
    }*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = super.onCreateView(inflater,container,savedInstanceState);
        Log.i(TAG, "onCreateView");
        return view;

     }
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onStop() {

        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDelayLoad = true;
        System.out.println(TAG+" onViewCreated");
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mFooterView = (LinearLayout) View.inflate(getActivity(), R.layout.listview_footer, null);
        mListView.addFooterView(mFooterView);
        mFooterView.setVisibility(View.GONE);

    }
    @Override
    public void initData(){
        showWaitDialog();
        Log.i(TAG,"initData");
        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id="+mGoodsId+"&curpage="+mPage+"&page="+Constants.PAGESIZE;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideWaitDialog();
                mDelayLoad = false;
                if (response.what == HttpStatus.SC_OK) {
                    Bundle bundle = response.getData();
                    mHasmore = bundle.getBoolean(HttpClientHelper.HASMORE);
                    mPageCount = bundle.getLong(HttpClientHelper.COUNT);
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        String goodsEvalJson = obj.getString("goods_eval_list");
                        List<GoodsEval> goodsEval = GoodsEval.arrayGoodsEvalFromData(goodsEvalJson);
                        mGoodsEvalAdapter = new GoodsEvalAdapter(getActivity(), goodsEval);
                        mListView.setAdapter(mGoodsEvalAdapter);
                        mFooterView.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(mGoodsEvalAdapter == null || mGoodsEvalAdapter.getCount() == 0){
            return;
        }
        boolean footerEnd = true;
        try {
            if(view.getPositionForView(mFooterView) == view.getLastVisiblePosition()){
                footerEnd = true;
            }else{
                footerEnd = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(footerEnd){
            mFooterView.setVisibility(View.VISIBLE);
            mPage++;
            if(!mHasmore && mPageCount > mPage){
                initData();
            }else{
                ((TextView)mFooterView.findViewById(R.id.upToLoadText)).setText("到底了");
                ((ProgressBar)mFooterView.findViewById(R.id.progressbar)).setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_all_fragment;
    }

    @Override
    protected void lazyLoad() {
        Log.i(TAG,"lazyLoad"+mDelayLoad);
        if(!mVisible || !mDelayLoad){
            return;
        }

        initData();
    }
}
