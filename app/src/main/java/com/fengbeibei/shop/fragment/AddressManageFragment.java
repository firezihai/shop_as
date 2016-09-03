package com.fengbeibei.shop.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.AccountManageActivity;
import com.fengbeibei.shop.adapter.AddressAdapter;
import com.fengbeibei.shop.bean.Address;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;
import com.fengbeibei.shop.interf.AddressItemEditInterface;
import com.fengbeibei.shop.utils.DialogUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 21:22
 */
public class AddressManageFragment extends BaseFragment implements View.OnClickListener,AddressItemEditInterface{
    @BindView(R.id.lv_address)
    ListView mListView;
    @BindView(R.id.tv_add_address)
    Button mAddAddrBtn;
    private String mkey;
    private Dialog mDialog;
    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_address_manage;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddHead(true);
    }

    @Override
    public void initView() {
        mkey = MyApplication.getInstance().getLoginKey();
        setHeadTitle(R.string.address_manage);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mAddAddrBtn.setOnClickListener(this);
        initData();
    }

    @Override
    public void initData() {

        String url= Constants.APP_URL +"act=member_address&op=address_list&key="+mkey;

        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        System.out.println(json);
                        if (obj.has("address_list")) {
                            String addressListJson = obj.getString("address_list");
                            List<Address> addressList = Address.arrayAddressFromData(addressListJson);
                            AddressAdapter adapter = new AddressAdapter(getActivity(), addressList);
                            adapter.setEditListener(AddressManageFragment.this);
                            mListView.setAdapter(adapter);
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
        if(v.getId() == R.id.tv_add_address){
            IntentHelper.accountManage(getActivity(), AccountManageActivity.ADDRESS_ADD);
        }
    }


    @Override
    public void setDefault(String addressId) {

    }

    @Override
    public void editAddress(Address address) {
        IntentHelper.editAddress(getActivity(), address);
    }

    @Override
    public void delAddress(final String addressId) {
        mDialog = DialogUtil.confirmDialog(getActivity(), "", "您确定要删除这个收货地址？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delAddr(addressId);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();

    }

    private void delAddr(String addressId){
        String url = Constants.APP_URL+"c=member_address&a=address_del&key="+mkey;
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("address_id", addressId);
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                mDialog.dismiss();
                if(response.what == HttpStatus.SC_OK){
                    String json = (String)response.obj;
                    if(json.equals("1")){
                        MyApplication.showToast("成功删除地址");
                        initData();
                    }
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mkey = MyApplication.getInstance().getLoginKey();
        initData();
    }
}
