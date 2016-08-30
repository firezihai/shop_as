package com.fengbeibei.shop.fragment.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
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

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.AreaSelectedAdapter;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.interf.Area;
import com.fengbeibei.shop.bean.City;
import com.fengbeibei.shop.bean.Province;
import com.fengbeibei.shop.common.Address;

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
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_province:
                setProvince();
                break;
            case R.id.rb_city:
                Province province = (Province)mProvinceBtn.getTag();
                setCity(province);
                break;
            case R.id.rb_district:
                setDistrict();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public AddressDialogFragment() {
        Bundle bundle = getArguments();
        if(bundle != null){
            mType = bundle.getInt("area_type",1);
            Address mAddress = bundle.getParcelable("address");
           switch (mType){
               case Area.TYPE_PROVINCE:
                   if(mAddress != null){
                        mProvinceBtn.setText(mAddress.getProvinceName());
                        mProvinceBtn.setTag(getProvince(mAddress));
                       setRadioButtonCheck(mCityBtn,true);
                   }
                   break;
               case Area.TYPE_CITY:
                   mProvinceBtn.setText(mAddress.getProvinceName());
                   mProvinceBtn.setTag(getProvince(mAddress));
                   Province province = getProvince(mAddress);
                   mCityBtn.setText(mAddress.getCityName());
                   mCityBtn.setTag(getCity(province,mAddress));
                   setRadioButtonCheck(mDistrictBtn, true);
                   break;
               case Area.TYPE_DISTRICT:

                   break;
           }
        }
    }

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

        return layout;
    }

    public void setProvince(){
        setProvinceBtn();
        mAdapter.setData(null);
        setBtnTouchListener(0);

    }

    public void setCity(Province province){
        setCityBtn();
        mAdapter.setData(null);
        setBtnTouchListener(1);
    }

    public void setDistrict(){
        setDistrict();
        mAdapter.setData(null);
        setBtnTouchListener(3);
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
        int type = 2;
        super.onStart();
        getDialog().setCanceledOnTouchOutside(true);
       Window window  = getDialog().getWindow();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        window.getAttributes().width = dm.widthPixels;
        window.getAttributes().height = dm.heightPixels/2;
        window.setGravity(Gravity.BOTTOM);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getInt("area_type");
            if(type == 1){
                type = 2;
            }
            mUnderline.getLayoutParams().width = dm.widthPixels/type;
        }
    }

    private Province getProvince(Address address){
        return new Province(address.getProvinceName(),address.getProvinceId());
    }
    private City getCity(Province province,Address address){
        return new City(province,address.getCityName(),address.getCityId());
    }

    public void setRadioButtonCheck(final RadioButton button ,final boolean checked){
        button.post(new Runnable() {
            @Override
            public void run() {
                button.setChecked(checked);
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
