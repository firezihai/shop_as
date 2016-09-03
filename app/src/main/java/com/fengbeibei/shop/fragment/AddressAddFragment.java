package com.fengbeibei.shop.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Address;
import com.fengbeibei.shop.bean.City;
import com.fengbeibei.shop.bean.District;
import com.fengbeibei.shop.bean.Province;
import com.fengbeibei.shop.callback.AddrAddAreaSelectedListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;
import com.fengbeibei.shop.fragment.dialog.AreaDialogBuilder;
import com.fengbeibei.shop.utils.PhoneUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-02 20:41
 */
public class AddressAddFragment extends BaseFragment implements View.OnClickListener{
    public static final int REQUEST_CONTACT = 0x110;
    private Address mAddress;
    @BindView(R.id.tv_consignor)
    EditText mUsername;
    @BindView(R.id.tv_phone)
    EditText mPhone;
    @BindView(R.id.rl_area_layout)
    RelativeLayout mAreaLayout;
    @BindView(R.id.tv_area)
    TextView mArea;
    @BindView(R.id.tv_address_info)
    EditText mAddressInfo;
    @BindView(R.id.ll_select_contact)
    LinearLayout mSelectContact;
    @BindView(R.id.ll_set_default)
    LinearLayout mSetDefault;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    private String mPhoneStr;
    private String mProvinceName;
    private String mProvinceId;
    private String mCityName;
    private String mCityId;
    private String mDistrictName;
    private String mDistrictId;
    private StringBuffer mAreaInfo;

    private int mType = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAddHead(true);
    }

    @Override
    public void initView() {
        setHeadTitle(R.string.add_address);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mAddress = getActivity().getIntent().getParcelableExtra("edit_addr");
        mSelectContact.setOnClickListener(this);
        mAreaLayout.setOnClickListener(this);
        mSetDefault.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        initData();

    }

    @Override
    public void initData() {
        mProvinceName = "";
        mProvinceId = "";
        mCityName = "";
        mCityId = "";
        mDistrictName = "";
        mDistrictId = "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_address;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_contact:
                selectContact();
                break;
            case R.id.rl_area_layout:
                if(mType > 1) {
                    District district = new District(new City(new Province(mProvinceName, mProvinceId), mCityName, mCityId), mDistrictName, mDistrictId);
                    com.fengbeibei.shop.common.Address address = new com.fengbeibei.shop.common.Address(district);
                    showAreaDialog(address);
                }else{
                    showAreaDialog(null);
                }
                break;
            case R.id.ll_set_default:
                if(mSetDefault.isSelected()){
                    mSetDefault.setSelected(false);
                }else{
                    mSetDefault.setSelected(true);
                }
                break;
            case R.id.btn_save:
                saveAddress();
                break;
        }
    }

    public void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CONTACT);
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
            if(cursor != null && cursor.moveToFirst()) {

                String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mUsername.setText(username);
                String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, null, null);
                while (phoneCursor.moveToNext() && cursor != null) {
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mPhoneStr = PhoneUtil.delHyphen(phone, "-");
                    mPhone.setText(PhoneUtil.addStarFormat(PhoneUtil.delHyphen(phone, "-"), 4));
                }

            }
        }
    }

    public void showAreaDialog(com.fengbeibei.shop.common.Address address){
        AreaDialogBuilder builder = new AreaDialogBuilder();
        builder.setAreaType(mType)
                .setAddress(address)
                .setAreaSelectedListener(new AddrAddAreaSelectedListener(this))
                .show(getFragmentManager());
    }

    public void saveAddress(){
        String key = MyApplication.getInstance().getLoginKey();
        String url = Constants.APP_URL+"act=member_address&op=address_add&key="+key;
        String username = mUsername.getText().toString();
        String areaInfo = mArea.getText().toString();
        String address = mAddressInfo.getText().toString();
        if(username.equals("") || username.length() <2){
            MyApplication.showToast("收货人姓名不能为空");
            return ;
        }
        if(areaInfo.equals("") || areaInfo.equals("null") ){
            MyApplication.showToast("所在地区错误！");
            return ;
        }
        if(address.equals("") || address.equals("null")){
            MyApplication.showToast("详细地址错误！");
            return ;
        }
        if(mCityId == null || mCityId.equals("")){
            MyApplication.showToast("城市信息错误！");
            return ;
        }
        if(mDistrictId == null || mDistrictId.equals("")){
            MyApplication.showToast("地区信息错误！");
            return ;
        }

        if(mPhoneStr == null || mPhoneStr.equals("")){
            MyApplication.showToast("手机不能为空！");
            return ;
        }
        Log.i("AddressEditFragment", address);
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("true_name",username);
        hashMap.put("area_info",areaInfo);
        hashMap.put("address",address);
        hashMap.put("mob_phone",mPhoneStr);
        hashMap.put("area_id", mDistrictId);
        hashMap.put("city_id", mCityId);
        if(mSetDefault.isSelected()){
            Log.i("AddressEditFragment","setDefault");
            hashMap.put("is_default", "1");
        }
        showLoadingDialog("");
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        if(obj.has("address_id") && !obj.getString("address_id").equals("")){
                            MyApplication.showToast("添加成功");
                            getActivity().finish();
                        }else{
                            String msg = obj.getString("error");
                            MyApplication.showToast(msg);
                            return;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    MyApplication.showToast("添加失败！");
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static void selectedAreaCallback(AddressAddFragment fragment,com.fengbeibei.shop.common.Address address){
        fragment.mProvinceName = address.getProvinceName();
        fragment.mProvinceId = address.getProvinceId();
        fragment.mCityName = address.getCityName();
        fragment.mCityId = address.getCityId();
        fragment.mDistrictName = address.getDistrictName();
        fragment.mDistrictId = address.getDistrictId();
        fragment.mArea.setText(fragment.mProvinceName+" "+fragment.mCityName+" "+fragment.mDistrictName);
        fragment.mType = 3;
    }
}
