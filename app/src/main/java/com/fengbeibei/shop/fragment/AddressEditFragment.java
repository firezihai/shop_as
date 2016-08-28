package com.fengbeibei.shop.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Address;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;
import com.fengbeibei.shop.utils.PhoneUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-28 12:07
 */
public class AddressEditFragment extends BaseFragment implements View.OnClickListener{
    public final static String ADDRESS_ID = "addressId";
    public static final int REQUEST_CONTACT = 0x110;
    private String mAddressId;
    @BindView(R.id.tv_consignor)
    TextView mUsername;
    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_area)
    TextView mArea;
    @BindView(R.id.tv_address_info)
    TextView mAddressInfo;
    @BindView(R.id.ll_select_contact)
    LinearLayout mSelectContact;
    @BindView(R.id.btn_save)
    Button mBtnSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddHead(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_address;
    }


    @Override
    public void initView() {
        setHeadTitle(R.string.edit_address);
        mAddressId = getActivity().getIntent().getStringExtra(ADDRESS_ID);
        mSelectContact.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        initData();
    }

    @Override
    public void initData() {
        String key = MyApplication.getInstance().getLoginKey();
        String url = Constants.APP_URL+"act=member_address&op=address_info";
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("key",key);
        hashMap.put("address_id", mAddressId);
        HttpClientHelper.asynPost(url,hashMap,new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    System.out.println(json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        if (obj.has("address_info")) {
                            Address address = Address.objectFromData(obj.getString("address_info"));
                            mUsername.setText(address.getTrueName());
                            mPhone.setText(address.getMobPhone());
                            mArea.setText(address.getAreaInfo());
                            mAddressInfo.setText(address.getAddress());
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
            case R.id.btn_save:
                saveAddress();
                break;
            case R.id.ll_select_contact:
                selectContact();
                break;
        }
    }

    public void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,REQUEST_CONTACT);
    }
    public void saveAddress(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CONTACT){
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);
           /* int nameIndex;
            while (cursor.moveToNext()){
                nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                mUsername.setText(cursor.getString(nameIndex));


            }*/
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            mUsername.setText(username);
            String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contact_id,null,null);
            while (phoneCursor.moveToNext()){
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mPhone.setText(PhoneUtil.addStarFormat(PhoneUtil.delHyphen(phone,"-"),4));
            }


        }
    }
}
