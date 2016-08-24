package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
public class GoodsGoodEvaluateFragment extends GoodsBaseFragment implements ListView.OnScrollListener{
    private static final String TAG ="GoodsGoodEvalFragment";
    private String mGoodsId;
    private int mPage =1;
    private LinearLayout mFooterView;
    private List<GoodsEval> mGoodsEval;
    private GoodsEvalAdapter mGoodsEvalAdapter;
    private Boolean mHasmore = true;
    private long mPageCount = 1;
    @BindView(R.id.listView)
    MyListView mListView;
    public static GoodsGoodEvaluateFragment newInstance(String goodsId){
        GoodsGoodEvaluateFragment goodsGoodEvaluateFragment = new GoodsGoodEvaluateFragment();
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
        mFooterView = (LinearLayout) View.inflate(getActivity(), R.layout.listview_footer, null);
        mListView.addFooterView(mFooterView);
        mFooterView.setVisibility(View.GONE);
    }

    @Override
    public void initData(){
        String url = Constants.APP_URL;
        url = url + "c=goods&a=goods_evaluate&goods_id="+mGoodsId+"&type=1&curpage="+mPage+"&page="+Constants.PAGESIZE;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();;
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
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(mGoodsEvalAdapter == null || mGoodsEvalAdapter.getCount() == 0){
            return;
        }
        boolean footerEnd = true;
        Log.i(TAG, "onScrollStateChanged footerEnd:" + footerEnd + " mHasmore:" + mHasmore + " mPageCount:" + mPageCount);
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

            if(mHasmore && mPageCount > mPage){
                mPage++;
                initData();
            }else{
                ((TextView)mFooterView.findViewById(R.id.upToLoadText)).setText("到底了");
                (mFooterView.findViewById(R.id.progressbar)).setVisibility(View.GONE);
            }

        }
    }
}
