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

import android.os.Message;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;

public class OrderListActivity extends BaseActivity{
	@BindView(R.id.orderListView)
    ListView mOrderListView;

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
        String url = Constants.ORDER_LIST_URL + "&key="+key;

        HttpClientHelper.asynGet(url, new CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                ArrayList<Order> orderList = new ArrayList<Order>();
                hideLoadingDialog();
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    try {
                        JSONObject objJson = new JSONObject(json);
                        JSONArray arr = new JSONArray(objJson.getString("order_group_list"));
                        int length = arr.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            ArrayList<Order> order = Order.newInstance(obj.getString("order_list"));
                            orderList.addAll(order);


                        }
                        OrderListAdapter orderListAdapter = new OrderListAdapter(OrderListActivity.this, R.layout.order_list_item);
                        orderListAdapter.setData(orderList);
                        mOrderListView.setAdapter(orderListAdapter);
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
        initData();
    }


}
