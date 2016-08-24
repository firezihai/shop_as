package com.fengbeibei.shop.activity;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.OrderListAdapter;
import com.fengbeibei.shop.bean.Order;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.widget.MyListView;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;

public class OrderListActivity extends BaseActivity{
	@BindView(R.id.orderListView)
    MyListView mOrderListView;
    @BindView(R.id.tv_all_order)
    TextView mAllOrderTab;
    @BindView(R.id.all_order_line)
    View mAllOrderUnderline;
    @BindView(R.id.tv_wait_pay)
    TextView mWaitPayTab;
    @BindView(R.id.wait_pay_line)
    View mWaitPayUnderline;
    @BindView(R.id.tv_wait_receipt)
    TextView mWaitReceiptTab;
    @BindView(R.id.wait_receipt_line)
    View mWaitReceiptUnderline;
    @BindView(R.id.tv_wait_comment)
    TextView mWaitCommentTab;
    @BindView(R.id.wait_comment_line)
    View mWaitCommentUnderline;

    private int mOrderType = 0;
    private int mPage = 1;
    private boolean mHasMore;
    private long mPageCount;
    private OrderListAdapter mOrderListAdapter;
    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.all_order);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initData() {
        showLoadingDialog();
        String key = MyApplication.getInstance().getLoginKey();
        String url = Constants.ORDER_LIST_URL + "curpage=5&key="+key+"&orderType="+mOrderType+"&page="+mPage;

        HttpClientHelper.asynGet(url, new CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                ArrayList<Order> orderList = new ArrayList<Order>();
                hideLoadingDialog();
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    Bundle bundle = response.getData();
                    mHasMore = bundle.getBoolean(HttpClientHelper.HASMORE);

                    mPageCount = bundle.getLong(HttpClientHelper.COUNT);
                    try {
                        JSONObject objJson = new JSONObject(json);
                        JSONArray arr = new JSONArray(objJson.getString("order_group_list"));
                        int length = arr.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            ArrayList<Order> order = Order.newInstance(obj.getString("order_list"));
                            orderList.addAll(order);


                        }
                        mOrderListAdapter.setData(orderList);
                        mOrderListAdapter.notifyDataSetChanged();
                        mOrderListView.footerVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void initView() {
        mLoadingLayout = R.layout.view_dialog_loading;
        mLoadingStyle = R.style.Dialog;
        mAllOrderTab.setOnClickListener(this);
        mWaitPayTab.setOnClickListener(this);
        mWaitReceiptTab.setOnClickListener(this);
        mWaitCommentTab.setOnClickListener(this);
        mOrderListAdapter = new OrderListAdapter(OrderListActivity.this, R.layout.order_list_item);
        mOrderListView.setAdapter(mOrderListAdapter);
        mOrderListView.setScrollCallListener(new MyListView.ScrollCallListener() {
            @Override
            public void updateData() {
                if(mHasMore && mPageCount >mPage){
                    mPage++;
                    initData();
                }else{
                    mOrderListView.setFooterViewText("到底了");
                }
            }
        });
        initData();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.tv_all_order:
               hideUnderLine(View.VISIBLE, View.GONE, View.GONE, View.GONE);
               mOrderType = 0;
               initData();
               break;
           case R.id.tv_wait_pay:
               mOrderType = 1;
               hideUnderLine(View.GONE, View.VISIBLE, View.GONE, View.GONE);
               initData();
               break;
           case R.id.tv_wait_receipt:
               mOrderType = 2;
               hideUnderLine(View.GONE,View.GONE,View.VISIBLE,View.GONE);
               initData();
               break;
           case R.id.tv_wait_comment:
               mOrderType = 3;
               hideUnderLine(View.GONE,View.GONE,View.GONE,View.VISIBLE);
               initData();
               break;
       }
    }

    private void hideUnderLine(int arg1,int arg2,int arg3,int arg4){
        mAllOrderUnderline.setVisibility(arg1);
        mWaitPayUnderline.setVisibility(arg2);
        mWaitReceiptUnderline.setVisibility(arg3);
        mWaitCommentUnderline.setVisibility(arg4);

    }


}
