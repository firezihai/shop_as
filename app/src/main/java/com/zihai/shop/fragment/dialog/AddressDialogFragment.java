package com.zihai.shop.fragment.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.zihai.shop.R;
import com.zihai.shop.adapter.AreaSelectedAdapter;
import com.zihai.shop.bean.City;
import com.zihai.shop.bean.District;
import com.zihai.shop.bean.Province;
import com.zihai.shop.common.Address;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.interf.Area;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AddressDialogFragment
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 9:34
 */
public class AddressDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener,RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.iv_close)
    ImageView mCloseDialog;
    @BindView(R.id.rg_area_select)
    RadioGroup mAreaSelect;
    @BindView(R.id.rb_province)
    RadioButton mProvinceBtn;
    @BindView(R.id.rb_city)
    RadioButton mCityBtn;
    @BindView(R.id.rb_district)
    RadioButton mDistrictBtn;
    @BindView(R.id.view_line)
    View mUnderline;
    @BindView(R.id.lv_address)
    ListView mListView;
    @BindView(R.id.rv_progress_layout)
    RelativeLayout mProgressLayout;
    public static final String AREA_TYPE = "area_type";
    //１：省，２：市，３：区
    private int mType = 1;
    private Address mAddress;

    private AddressDialogFragment.OnAreaSelectedListener mOnAreaSelectedListener;
    private AreaSelectedAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME, R.style.dialog_float_up);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_select_area, container, false);
        ButterKnife.bind(this, layout);
        mAreaSelect.setOnCheckedChangeListener(this);
        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mAdapter = new AreaSelectedAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        setBundle();
        return layout;
    }

    private void setBundle(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mType = bundle.getInt("area_type",1);
            mAddress = bundle.getParcelable("address");
            switch (mType){
                case Area.TYPE_PROVINCE:
                    if(mAddress != null){
                        mProvinceBtn.setText(mAddress.getProvinceName());
                        mProvinceBtn.setTag(getProvince(mAddress));
                        setRadioButtonCheck(mCityBtn,true);
                        return;
                    }
                    setProvince();
                    break;
                case Area.TYPE_CITY:
                    if(mAddress != null) {
                        mProvinceBtn.setText(mAddress.getProvinceName());
                        mProvinceBtn.setTag(getProvince(mAddress));
                        Province province = getProvince(mAddress);
                        mCityBtn.setText(mAddress.getCityName());
                        mCityBtn.setTag(getCity(province, mAddress));
                        setRadioButtonCheck(mDistrictBtn, true);
                        return;
                    }
                    setCity((Province)mProvinceBtn.getTag());
                    break;
                case Area.TYPE_DISTRICT:

                    mProvinceBtn.setText(mAddress.getProvinceName());
                    mProvinceBtn.setTag(getProvince(mAddress));
                    Province province2 = getProvince(mAddress);
                    City city = getCity(province2, mAddress);
                    mCityBtn.setText(mAddress.getCityName());
                    mCityBtn.setTag(city);
                    mDistrictBtn.setText(mAddress.getDistrictName());
                    mDistrictBtn.setTag(getDistrict(city,mAddress));
                    setRadioButtonCheck(mDistrictBtn, true);
                    break;
            }
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_province:
                setProvince();
                break;
            case R.id.rb_city:
                Province province = (Province)mProvinceBtn.getTag();
                if(province == null){
                    return ;
                }
                setCity(province);
                break;
            case R.id.rb_district:
                City city = (City)mCityBtn.getTag();
                if(city == null){
                    return;
                }
                setDistrict(city);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("AddressEditFragment", mAdapter.getArea(position).getType() + "");
        switch (mAdapter.getArea(position).getType()){
            case Area.TYPE_PROVINCE:
                Province province = (Province)mAdapter.getArea(position);
                mProvinceBtn.setText(province.getName());
                mProvinceBtn.setTag(province);
                setRadioButtonCheck(mCityBtn, true);
                mAdapter.setData(null);
                Log.i("AddressDialogFragment", "item province");
                // getAreaList(province.getId(), Area.TYPE_CITY);

                break;
            case Area.TYPE_CITY:
                City city = (City)mAdapter.getArea(position);
                mCityBtn.setText(city.getName());
                mCityBtn.setTag(city);
                mAdapter.setData(null);
                setRadioButtonCheck(mDistrictBtn, true);
                Log.i("AddressDialogFragment", "item city");
                //getAreaList(city.getId(),Area.TYPE_DISTRICT);
                break;
            case Area.TYPE_DISTRICT:
                District district = (District)mAdapter.getArea(position);
                Address address = new Address(district);
                mDistrictBtn.setText(district.getName());
                mDistrictBtn.setTag(district);
                mAdapter.setData(null);
                mOnAreaSelectedListener.onAreaSelected(address);
                dismiss();
                break;
        }

    }

    public void setProvince(){
        setProvinceBtn();
        mAdapter.setData(null);
        setBtnTouchListener(0);
        getAreaList("0",Area.TYPE_PROVINCE);
    }

    public void setCity(Province province){
        setCityBtn();
        mAdapter.setData(null);
        setBtnTouchListener(1);
        Log.i("AddressDialogFragment", "setCity");
        getAreaList(province.getId(),Area.TYPE_CITY);
    }

    public void setDistrict(City city){
        setDistrictBtn();
        mAdapter.setData(null);
        setBtnTouchListener(2);
        getAreaList(city.getId(), Area.TYPE_DISTRICT);
    }


    public void setProvinceBtn(){
        if(!(mProvinceBtn.getVisibility() == View.VISIBLE)){
            return ;
        }
        mProvinceBtn.setTag(null);
        mProvinceBtn.setText(R.string.select_province);
        setCityBtn();
    }

    public void setCityBtn(){
        mCityBtn.setTag(null);
        mCityBtn.setText(R.string.select_city);
        setDistrictBtn();
    }

    public void setDistrictBtn(){
        mDistrictBtn.setTag(null);
        mDistrictBtn.setText(R.string.select_district);
    }
    @Override
    public void onStart() {
        int type = 3;
        super.onStart();
        getDialog().setCanceledOnTouchOutside(true);
        Window window  = getDialog().getWindow();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        window.getAttributes().width = dm.widthPixels;
        window.getAttributes().height = dm.heightPixels/2;
        window.setGravity(Gravity.BOTTOM);
        Bundle bundle = getArguments();
        if(bundle != null){
            int areaType = bundle.getInt("area_type");
            if(areaType == 1){
                areaType = type;
            }
            mUnderline.getLayoutParams().width = dm.widthPixels/areaType;
        }
    }

    private Province getProvince(Address address){
        return new Province(address.getProvinceName(),address.getProvinceId());
    }
    private City getCity(Province province,Address address){
        return new City(province,address.getCityName(),address.getCityId());
    }
    private District getDistrict(City city,Address address){
        return new District(city,address.getDistrictName(),address.getDistrictId());
    }
    public void setRadioButtonCheck(final RadioButton button ,final boolean checked){
        button.post(new Runnable() {
            @Override
            public void run() {
                button.setChecked(checked);
            }
        });
    }

    public void getAreaList(final String areaId,final int type){
        String key = MyApplication.getInstance().getLoginKey();
        Log.i("AddressDialogFragment", "getAreaList");
        String url = Constants.APP_URL+"act=member_address&op=area_list&key="+key;
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("area_id",areaId);
        mProgressLayout.setVisibility(View.VISIBLE);
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                mProgressLayout.setVisibility(View.GONE);
                if(response.what == HttpStatus.SC_OK){

                    String json = (String)response.obj;
                    Log.i("AddressDialogFragment",json);
                    try{
                        JSONObject obj = new JSONObject(json);
                        List<Area> areaList = new ArrayList<Area>();
                        String areaJson = obj.getString("area_list");
                        JSONArray arr = new JSONArray(areaJson);
                        int size = arr.length();
                        switch (type){
                            case Area.TYPE_PROVINCE:

                                for(int i=0;i<size;i++){
                                    JSONObject itemObj = arr.getJSONObject(i);
                                    Province  province = new Province(itemObj.getString("area_name"),itemObj.getString("area_id"));
                                    areaList.add(province);
                                }
                                mAdapter.setData(areaList);
                                break;
                            case Area.TYPE_CITY:
                                for(int i=0;i<size;i++){
                                    JSONObject itemObj = arr.getJSONObject(i);
                                    City  city = new City((Province)mProvinceBtn.getTag(),itemObj.getString("area_name"),itemObj.getString("area_id"));
                                    areaList.add(city);
                                }
                                mAdapter.setData(areaList);
                                break;
                            case Area.TYPE_DISTRICT:
                                for(int i=0;i<size;i++){
                                    JSONObject itemObj = arr.getJSONObject(i);
                                    District district = new District((City)mCityBtn.getTag(),itemObj.getString("area_name"),itemObj.getString("area_id"));
                                    areaList.add(district);
                                }
                                mAdapter.setData(areaList);
                                break;
                            default:
                                break;
                        }
                        //List<Area> areaList =

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else{
                    MyApplication.showToast("内容无法加载，请重试！");
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public String getName(){
        return "SelectAreaDialog";
    }

    @SuppressWarnings("NewApi")
    private void setBtnTouchListener(int index) {
        mUnderline.animate().translationX(mUnderline.getWidth()*index).setDuration(200).start();
        RegOnTouchListener onTouchListener;
        if(index == 0){
            onTouchListener = new RegOnTouchListener(this,R.string.select_your_province);
            mCityBtn.setOnTouchListener(onTouchListener);
            mDistrictBtn.setOnTouchListener(onTouchListener);
            return;
        }
        if(index == 1){
            onTouchListener   = new RegOnTouchListener(this,R.string.select_your_city);
            mCityBtn.setOnTouchListener(null);
            mDistrictBtn.setOnTouchListener(onTouchListener);
            return ;
        }
        mDistrictBtn.setOnTouchListener(null);
    }
    public void setOnAreaSelectedListener(OnAreaSelectedListener onAreaSelectedListener) {
        mOnAreaSelectedListener = onAreaSelectedListener;
    }

    public interface OnAreaSelectedListener{
        void onAreaSelected(Address address);
    }

    class RegOnTouchListener implements View.OnTouchListener{
        private int mMsgResId;
        private AddressDialogFragment mFragment;

        public RegOnTouchListener(AddressDialogFragment fragment, int msgResId) {
            mFragment= fragment;
            mMsgResId = msgResId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                MyApplication.showToast(mFragment.getString(mMsgResId));
            }
            return false;
        }
    }
}
