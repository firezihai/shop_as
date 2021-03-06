package com.zihai.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.zihai.shop.R;
import com.zihai.shop.adapter.GoodsEvalAdapter;
import com.zihai.shop.bean.GoodsEval;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.fragment.Base.GoodsBaseFragment;
import com.zihai.shop.widget.MyListView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by thinkpad on 2016-08-01.
 */
public class GoodsAllEvaluateFragment extends GoodsBaseFragment implements MyListView.ScrollCallListener{
    private String TAG = "GoodsAllEvalFragment";
    private String mGoodsId;
    private int mPage =1;
    private GoodsEvalAdapter mGoodsEvalAdapter;
    private Boolean mHasmore;
    private long mPageCount;
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
        mGoodsId = getArguments() != null ? getArguments().getString("goodsId") : "0" ;
    }




    @Override
    public void initView() {
        //super.initView(view);
        mGoodsEvalAdapter = new GoodsEvalAdapter(getActivity());
        mListView.setAdapter(mGoodsEvalAdapter);
        mListView.setScrollCallListener(this);
        mDelayLoad = true;
    }
    @Override
    public void initData(){

        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id="+mGoodsId+"&curpage="+mPage+"&page=10";
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
                        mGoodsEvalAdapter.notifyDataSetChanged();
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
    protected int getLayoutId() {
        return R.layout.goods_evaluate_all_fragment;
    }

    @Override
    protected void lazyLoad() {
        if(!mVisible || !mDelayLoad){
            return;
        }
        showLoadingDialog();
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
