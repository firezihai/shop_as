package com.fengbeibei.shop.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Address;
import com.fengbeibei.shop.bean.City;
import com.fengbeibei.shop.bean.District;
import com.fengbeibei.shop.bean.Province;
import com.fengbeibei.shop.callback.DeliveryAreaSelectedListener;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.dialog.AreaDialogBuilder;
import com.fengbeibei.shop.utils.PhoneUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-10 12:35
 */
public class UpdateDeliveryActivity extends BaseActivity{
    public static final int REQUEST_CONTACT = 0x110;
    public static final int RESULT_UPDATE_DELIVERY = 0x1;
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
        setHeadTitle(R.string.edit_delivery_address);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    public void initView() {
        mAddress = getIntent().getParcelableExtra(EXTRA_ADDRESS);
        mSelectContact.setOnClickListener(this);
        mAreaLayout.setOnClickListener(this);
        mSetDefault.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        initData();
    }

    @Override
    public void initData() {
        mUsername.setText(mAddress.getTrueName());
        mPhone.setText(mAddress.getMobPhone());
        mArea.setText(mAddress.getAreaInfo());
        mAddressInfo.setText(mAddress.getAddress());
        if(mAddress.getIsDefault().equals("1")){
            mCheckBox.setSelected(true);
        }
        mAreaInfo = new StringBuffer();
        String[] areaInfo = mAddress.getAreaInfo().split("\\s");
        mPhoneStr = mAddress.getMobPhone();
        mProvinceName = areaInfo[0];
        mCityName = areaInfo[1];
        mCityId = mAddress.getCityId();
        mDistrictId = mAddress.getAreaId();
        mDistrictName = areaInfo[2];
        mAreaInfo.setLength(0);
        mAreaInfo.append(mProvinceName);
        mAreaInfo.append(mCityName);
        mAreaInfo.append(mDistrictName);
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
                District district = new District(new City(new Province(mProvinceName,"1"),mCityName,mCityId),mDistrictName,mDistrictId);
                com.fengbeibei.shop.common.Address address = new com.fengbeibei.shop.common.Address(district);
                showAreaDialog(address);
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
        startActivityForResult(intent, REQUEST_CONTACT);
    }
    public void saveAddress(){
        String key = MyApplication.getInstance().getLoginKey();
        String url = Constants.APP_URL+"act=member_address&op=address_edit&key="+key;
        String username = mUsername.getText().toString();
        String areaInfo = mArea.getText().toString();
        String address = mAddressInfo.getText().toString();
        if(username.equals("") || username.length() <2){
            MyApplication.showToast("收货人姓名不能为空");
            return ;
        }
        if(areaInfo.equals("") ){
            MyApplication.showToast("所在地区错误！");
            return ;
        }
        if(address.equals("")){
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
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("address_id",mAddress.getAddressId());
        hashMap.put("true_name", username);
        hashMap.put("area_info", areaInfo);
        hashMap.put("address", address);
        hashMap.put("mob_phone", mPhoneStr);
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
                    if (json.equals("1")) {
                        backBuy();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(json);
                            if (obj.has("error")) {
                                String msg = obj.getString("error");
                                MyApplication.showToast(msg);
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    MyApplication.showToast("保存失败！");
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
        intent.putExtra("address",mAddress);
        setResult(RESULT_UPDATE_DELIVERY, intent);
      //  startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CONTACT){
            Uri uri = data.getData();
            ContentResolver cr = getContentResolver();
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
                while (phoneCursor != null && phoneCursor.moveToNext()) {
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mPhoneStr = PhoneUtil.delHyphen(phone, "-");
                    mPhone.setText(PhoneUtil.addStarFormat(PhoneUtil.delHyphen(phone, "-"), 4));
                }
            }


        }
    }

    public void showAreaDialog(com.fengbeibei.shop.common.Address address){
        AreaDialogBuilder builder = new AreaDialogBuilder();
        builder.setAreaType(3)
                .setAddress(address)
                .setAreaSelectedListener(new DeliveryAreaSelectedListener(this))
                .show(getSupportFragmentManager());
    }

    public static void onAreaSelected(UpdateDeliveryActivity activity,com.fengbeibei.shop.common.Address address) {
        activity.mProvinceName = address.getProvinceName();
        activity.mProvinceId = address.getProvinceId();
        activity.mCityName = address.getCityName();
        activity.mCityId = address.getCityId();
        activity.mDistrictName = address.getDistrictName();
        activity.mDistrictId = address.getDistrictId();
        activity.mArea.setText(activity.mProvinceName+" "+activity.mCityName+" "+activity.mDistrictName);
    }
}
