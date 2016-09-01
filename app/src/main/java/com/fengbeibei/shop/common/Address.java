package com.fengbeibei.shop.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.fengbeibei.shop.bean.City;
import com.fengbeibei.shop.bean.District;
import com.fengbeibei.shop.bean.Province;

import java.io.Serializable;


/**
 * Address
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 10:59
 */
public class Address implements Parcelable , Serializable {
    public static final Parcelable.Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return null;
        }
    };
    private static final long serialVersionUID = 1;
    private String mProvinceName;
    private String mProvinceId;
    private String mCityName;
    private String mCityId;
    private String mDistrictName;
    private String mDistrictId;

    public Address(){
        mProvinceName = "";
        mProvinceId = "";
        mCityName = "";
        mCityId = "";
        mDistrictName = "";
        mDistrictId = "";
    }
    public Address(Parcel parcel) {
        mProvinceName = parcel.readString();
        mProvinceId = parcel.readString();
        mCityName = parcel.readString();
        mCityId = parcel.readString();
        mDistrictName = parcel.readString();
        mDistrictId = parcel.readString();
    }

    public Address(Province province){
        this();
        mProvinceName = province.getName();
        mProvinceId = province.getId();
    }

    public Address(City city){
        this(city.getProvince());
        mCityName = city.getName();
        mCityId = city.getId();
    }

    public Address(District district){
        this(district.getCity());
        mDistrictName = district.getName();
        mDistrictId = district.getId();
    }
    public String getDistrictId() {
        return mDistrictId;
    }

    public String getDistrictName() {
        return mDistrictName;
    }

    public String getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getProvinceId() {
        return mProvinceId;
    }

    public String getProvinceName() {
        return mProvinceName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mProvinceName);
        parcel.writeString(mProvinceId);
        parcel.writeString(mCityName);
        parcel.writeString(mCityId);
        parcel.writeString(mDistrictName);
        parcel.writeString(mDistrictId);
    }

    @Override
    public String toString() {
        return "Address{" +
                "mCityId='" + mCityId + '\'' +
                ", mProvinceName='" + mProvinceName + '\'' +
                ", mProvinceId='" + mProvinceId + '\'' +
                ", mCityName='" + mCityName + '\'' +
                ", mDistrictName='" + mDistrictName + '\'' +
                ", mDistrictId='" + mDistrictId + '\'' +
                '}';
    }
}
