package com.fengbeibei.shop.activity;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.LogisticsAdapter;
import com.fengbeibei.shop.bean.Logistics;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * OrderLogisticsActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 10:50
 */
public class OrderLogisticsActivity extends BaseActivity{
    @BindView(R.id.lv_logistics)
    ListView mListView;
    private String mOrderId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initData() {
      //  String url = Constants.APP_URL+"&act=member_order&op=search_deliver";
        String url = "http://192.168.1.125/mobile/index.php?act=member_order&op=get_express&key=7b28f6da02aa777bb4f62fbe63dd7c06&code=11428020802";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("code",mOrderId);
        HttpClientHelper.asynPost(url, params, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if(response.what == HttpStatus.SC_OK){
                    String json = (String)response.obj;
                    Logistics logistics = Logistics.objectFromData(json);

                    List<Logistics.DeliverInfoBean> arrayList = logistics.getDeliverInfo();
                    LogisticsAdapter adapter = new LogisticsAdapter(arrayList,OrderLogisticsActivity.this);
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void initView() {
       // mOrderId = getIntent().getStringExtra("orderId");
        mOrderId = "11428020802";

        initData();
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.logistics_info);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
