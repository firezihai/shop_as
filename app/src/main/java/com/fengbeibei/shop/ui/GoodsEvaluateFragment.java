package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.GoodsEval;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.interf.GoodsFragmentListener;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsEvaluateFragment extends Fragment implements GoodsFragmentListener ,SwipeRefreshLayout.OnRefreshListener {

    private String mGoodsId;
    @BindView(R.id.listView)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static GoodsEvaluateFragment newInstance(String goodsId){
        GoodsEvaluateFragment fragment = new GoodsEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodsId", goodsId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.goods_evaluate_fragment,container,false);
        Bundle bundle = getArguments();
        if(bundle != null){
            mGoodsId = bundle.getString("goodsId");
        }
        initData(mGoodsId);
        return layout;
    }

    public void initData(String goodId){
        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id=6358&curpage=1&page=10";
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if(response.what== HttpStatus.SC_OK){
                    String json = (String)response.obj;
                    List<GoodsEval> goodsEval = GoodsEval.arrayGoodsEvalFromData(json);

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void onUpdateUI(String data) {
        initData(data);
    }

    @Override
    public void onRefresh() {

    }
}
