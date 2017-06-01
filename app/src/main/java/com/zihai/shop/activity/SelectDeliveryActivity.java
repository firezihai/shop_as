package com.zihai.shop.activity;

import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zihai.shop.R;
import com.zihai.shop.adapter.AddressAdapter;
import com.zihai.shop.bean.Address;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.IntentHelper;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.interf.AddressItemEditInterface;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-09 21:59
 */
public class SelectDeliveryActivity extends BaseActivity implements AddressItemEditInterface ,ListView.OnItemClickListener{
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.btn_add_address)
    Button mBtnAddAddress;
    private String mKey;
    private List<Address> mAddressList;
    private Address mAddress;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_delivery;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.select_delivery_address);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mKey = MyApplication.getInstance().getLoginKey();
        mAddress = getIntent().getParcelableExtra("address");
        mBtnAddAddress.setOnClickListener(this);
        initData();
    }

    @Override
    public void initData() {

        String url= Constants.APP_URL +"act=member_address&op=address_list&key="+mKey;

        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;

                    try {
                        JSONObject obj = new JSONObject(json);
                        if (obj.has("address_list")) {
                            String addressListJson = obj.getString("address_list");
                            mAddressList = Address.arrayAddressFromData(addressListJson);
                            AddressAdapter adapter = new AddressAdapter(SelectDeliveryActivity.this, mAddressList);
                            adapter.setViewType(1);
                            adapter.setSelected(mAddress.getAddressId());
                            adapter.setEditListener(SelectDeliveryActivity.this);
                            mListView.setAdapter(adapter);
                            mListView.setOnItemClickListener(SelectDeliveryActivity.this);
                            adapter.notifyDataSetChanged();
                        }
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_address:
                IntentHelper.addDeliveryAddr(this,mAddress);
                break;
        }
    }

    @Override
    public void delAddress(String addressId) {

    }

    @Override
    public void setDefault(String addressId) {

    }

    @Override
    public void editAddress(Address address) {
        IntentHelper.editDeliveryAddr(this, address);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        backBuy(mAddressList.get(position));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            backBuy(mAddress);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case UpdateDeliveryActivity.RESULT_UPDATE_DELIVERY:
                backBuy((Address)data.getParcelableExtra(UpdateDeliveryActivity.EXTRA_ADDRESS));
                break;
            case AddDeliveryActivity.RESULT_ADD_DELIVERY:
                backBuy((Address)data.getParcelableExtra(AddDeliveryActivity.EXTRA_ADDRESS));
                break;
        }
    }

    public void backBuy(Address address){
        Intent intent = new Intent();
        intent.putExtra("address",address);
        setResult(RESULT_OK, intent);
        finish();
    }
}
