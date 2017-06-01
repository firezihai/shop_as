package com.zihai.shop.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.bean.Address;
import com.zihai.shop.bean.City;
import com.zihai.shop.bean.District;
import com.zihai.shop.bean.Province;
import com.zihai.shop.callback.AddDeliveryAreaSelectedListener;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.fragment.dialog.AreaDialogBuilder;
import com.zihai.shop.utils.PhoneUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-10 20:28
 */
public class AddDeliveryActivity extends BaseActivity{
    public static final int REQUEST_CONTACT = 0x110;
    public static final int RESULT_ADD_DELIVERY = 0x2;
    public static final String EXTRA_ADDRESS = "address";
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
    @BindView(R.id.cb_set_default)
    CheckBox mCheckBox;
    @BindView(R.id.btn_save)
    Button mBtnSave;

    private String mProvinceName;
    private String mProvinceId;
    private String mCityName;
    private String mCityId;
    private String mDistrictName;
    private String mDistrictId;
    private StringBuffer mAreaInfo;
    private String mPhoneStr;
    private int mType = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_delivery;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.add_delivery_address);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    public void initView() {
        mBtnSave.setOnClickListener(this);
        mAreaLayout.setOnClickListener(this);
        mSetDefault.setOnClickListener(this);
        mSelectContact.setOnClickListener(this);
        mAddress = new Address();
    }

    @Override
    public void initData() {

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
            case R.id.rl_area_layout:
                if(mType > 1) {
                    District district = new District(new City(new Province(mProvinceName, mProvinceId), mCityName, mCityId), mDistrictName, mDistrictId);
                    com.zihai.shop.common.Address address = new com.zihai.shop.common.Address(district);
                    showAreaDialog(address);
                }else{
                    showAreaDialog(null);
                }
                break;
            case R.id.ll_set_default:
                if(mCheckBox.isChecked()){
                    mCheckBox.setChecked(false);
                }else{
                    mCheckBox.setChecked(true);
                }
                break;
        }
    }

    public void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,REQUEST_CONTACT);
    }

    public void saveAddress(){
        String url = Constants.APP_URL+"act=member_address&op=address_add&key="+mKey;
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
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("true_name",username);
        hashMap.put("area_info",areaInfo);
        hashMap.put("address",address);
        hashMap.put("mob_phone",mPhoneStr);
        hashMap.put("area_id", mDistrictId);
        hashMap.put("city_id", mCityId);

        if(mCheckBox.isChecked()){
            hashMap.put("is_default", "1");
            mAddress.setIsDefault("1");
        }
        mAddress.setAreaId(mDistrictId);
        mAddress.setCityId(mCityId);
        mAddress.setMobPhone(mPhoneStr);
        mAddress.setTrueName(username);
        mAddress.setAreaInfo(areaInfo);
        mAddress.setAddress(address);
        showLoadingDialog("");
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        if (obj.has("address_id") && !obj.getString("address_id").equals("")) {
                            backBuy();
                        } else {
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

    private void backBuy(){
        Intent intent = new Intent();
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_ADDRESS,mAddress);
        setResult(RESULT_ADD_DELIVERY, intent);
        //  startActivity(intent);
        finish();
    }

    public void showAreaDialog(com.zihai.shop.common.Address address){
        AreaDialogBuilder builder = new AreaDialogBuilder();
        builder.setAreaType(mType)
                .setAddress(address)
                .setAreaSelectedListener(new AddDeliveryAreaSelectedListener(this))
                .show(getSupportFragmentManager());
    }

    public static void onAreaSelected(AddDeliveryActivity activity,com.zihai.shop.common.Address address) {
        activity.mProvinceName = address.getProvinceName();
        activity.mProvinceId = address.getProvinceId();
        activity.mCityName = address.getCityName();
        activity.mCityId = address.getCityId();
        activity.mDistrictName = address.getDistrictName();
        activity.mDistrictId = address.getDistrictId();
        activity.mArea.setText(activity.mProvinceName+" "+activity.mCityName+" "+activity.mDistrictName);
        activity.mType = 3;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CONTACT == requestCode){
            Uri uri = data.getData();
            ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(uri,null,null,null,null);
            if(cursor != null &&  cursor.moveToFirst()){
                String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mUsername.setText(username);
                String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contact_id,null,null);
                while(cursorPhone != null &&cursorPhone.moveToNext()){
                    String phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mPhone.setText(phone);
                    mPhoneStr = PhoneUtil.delHyphen(phone, "-");
                    mPhone.setText(PhoneUtil.addStarFormat(PhoneUtil.delHyphen(phone, "-"), 4));
                }

            }
        }
    }
}
