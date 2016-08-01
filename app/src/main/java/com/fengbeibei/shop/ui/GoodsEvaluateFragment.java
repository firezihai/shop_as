package com.fengbeibei.shop.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.fengbeibei.shop.interf.GoodsFragmentListener;
import com.fengbeibei.shop.widget.MyListView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodsEvaluateFragment extends Fragment implements GoodsFragmentListener,AbsListView.OnScrollListener{

    private String mGoodsId;
    private int mPage =1;
    private LinearLayout mFooterView;
    private List<GoodsEval> mGoodsEval;
    private GoodsEvalAdapter mGoodsEvalAdapter;
    private Boolean mHasmore = true;
    private long mPageCount = 1;
    @BindView(R.id.listView)
    MyListView mListView;
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
        ButterKnife.bind(this, layout);
        Bundle bundle = getArguments();
        if(bundle != null){
            mGoodsId = bundle.getString("goodsId");
        }
        mGoodsId = "9597";
        mFooterView = (LinearLayout)View.inflate(getActivity(),R.layout.listview_footer,null);
        mListView.addFooterView(mFooterView);
        mFooterView.setVisibility(View.GONE);

        initData();
        return layout;
    }

    public void initData(){
        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id="+mGoodsId+"&curpage="+mPage+"&page="+Constants.PAGESIZE;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    Bundle bundle = response.getData();
                    mHasmore = bundle.getBoolean(HttpClientHelper.HASMORE);
                    mPageCount = bundle.getLong(HttpClientHelper.COUNT);
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        String goodsEvalJson = obj.getString("goods_eval_list");
                        List<GoodsEval> goodsEval = GoodsEval.arrayGoodsEvalFromData(goodsEvalJson);
                        mGoodsEvalAdapter= new GoodsEvalAdapter(getActivity(),goodsEval);
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
    public void onUpdateUI(String data) {
        mGoodsId = data;
        initData();
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
}
