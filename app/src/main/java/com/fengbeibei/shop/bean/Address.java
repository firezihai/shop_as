package com.fengbeibei.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-28 11:23
 */
public class Address {

    /**
     * address_id : 245
     * member_id : 186
     * true_name : 李龙海
     * area_id : 2953
     * city_id : 279
     * area_info : 湖南 邵阳市 新宁县
     * address : （蜂贝贝家居新宁店) 新宁县金石镇棉花糖路富丽城营销中心31～36号，电话：0739-2918666
     * tel_phone :
     * mob_phone : 18870889397
     * is_default : 0
     * dlyp_id : 0
     * chain_id : 1
     */

    @SerializedName("address_id")
    private String addressId;
    @SerializedName("member_id")
    private String memberId;
    @SerializedName("true_name")
    private String trueName;
    @SerializedName("area_id")
    private String areaId;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("area_info")
    private String areaInfo;
    @SerializedName("address")
    private String address;
    @SerializedName("tel_phone")
    private String telPhone;
    @SerializedName("mob_phone")
    private String mobPhone;
    @SerializedName("is_default")
    private String isDefault;
    @SerializedName("dlyp_id")
    private String dlypId;
    @SerializedName("chain_id")
    private String chainId;

    public static Address objectFromData(String str) {

        return new Gson().fromJson(str, Address.class);
    }

    public static List<Address> arrayAddressFromData(String str) {

        Type listType = new TypeToken<ArrayList<Address>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getDlypId() {
        return dlypId;
    }

    public void setDlypId(String dlypId) {
        this.dlypId = dlypId;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
