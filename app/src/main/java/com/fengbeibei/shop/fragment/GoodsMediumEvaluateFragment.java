package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.GoodsEvalAdapter;
import com.fengbeibei.shop.bean.GoodsEval;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.fragment.Base.GoodsBaseFragment;
import com.fengbeibei.shop.widget.MyListView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by thinkpad on 2016-08-01.
 */
public class GoodsMediumEvaluateFragment extends GoodsBaseFragment implements MyListView.ScrollCallListener{
    private static final String TAG ="GoodsGoodEvalFragment";
    private String mGoodsId;
    private int mPage =1;
    private GoodsEvalAdapter mGoodsEvalAdapter;
    private Boolean mHasmore = true;
    private long mPageCount = 1;
    @BindView(R.id.listView)
    MyListView mListView;
    public static GoodsMediumEvaluateFragment newInstance(String goodsId){
        GoodsMediumEvaluateFragment goodsGoodEvaluateFragment = new GoodsMediumEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId",goodsId);
        goodsGoodEvaluateFragment.setArguments(bundle);
        return goodsGoodEvaluateFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.goods_evaluate_all_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0";
    }

    @Override
    public void initView() {
        mDelayLoad = true;
        mGoodsEvalAdapter = new GoodsEvalAdapter(getActivity());
        mListView.setAdapter(mGoodsEvalAdapter);
        mListView.setScrollCallListener(this);
    }

    @Override
    public void initData(){
        showLoadingDialog();
        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id="+mGoodsId+"&type=1&curpage="+mPage+"&page="+Constants.PAGESIZE;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
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
                        mGoodsEvalAdapter.setData(goodsEval);
                        mListView.footerVisibility(View.GONE);

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
    protected void lazyLoad() {
        Log.i(TAG,"lazyLoad "+mDelayLoad + " mVisible "+mVisible);
        if(!mVisible || !mDelayLoad){
            return;
        }
        Log.i(TAG, "lazyLoad" + mDelayLoad + " mVisible " + mVisible);
        initData();
    }

    @Override
    public void setUpdate(String data) {
        mGoodsId = data;
        mDelayLoad = true;
    }

    @Override
    public void updateData() {
        if (mHasmore && mPageCount > mPage) {
            mPage++;
            initData();
        } else {
            mListView.setFooterViewText("到底了");
        }
    }
}
