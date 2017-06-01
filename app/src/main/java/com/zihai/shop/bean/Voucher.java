package com.zihai.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-09 10:30
 */
public class Voucher {

    /**
     * voucher_id : 2
     * voucher_code : 120526733638876053
     * voucher_t_id : 3
     * voucher_title : 我是代金券
     * voucher_desc : gggggggggggggggggg
     * voucher_start_date : 1473389638
     * voucher_end_date : 1481126400
     * voucher_price : 10
     * voucher_limit : 100.00
     * voucher_store_id : 6
     * voucher_state : 1
     * voucher_active_date : 1473389638
     * voucher_type : 0
     * voucher_owner_id : 53
     * voucher_owner_name : 段指
     * voucher_order_id : null
     * desc : 面额10元 有效期至 2016-12-08
     */

    @SerializedName("voucher_id")
    private String voucherId;
    @SerializedName("voucher_code")
    private String voucherCode;
    @SerializedName("voucher_t_id")
    private String voucherTId;
    @SerializedName("voucher_title")
    private String voucherTitle;
    @SerializedName("voucher_desc")
    private String voucherDesc;
    @SerializedName("voucher_start_date")
    private String voucherStartDate;
    @SerializedName("voucher_end_date")
    private String voucherEndDate;
    @SerializedName("voucher_price")
    private String voucherPrice;
    @SerializedName("voucher_limit")
    private String voucherLimit;
    @SerializedName("voucher_store_id")
    private String voucherStoreId;
    @SerializedName("voucher_state")
    private String voucherState;
    @SerializedName("voucher_active_date")
    private String voucherActiveDate;
    @SerializedName("voucher_type")
    private String voucherType;
    @SerializedName("voucher_owner_id")
    private String voucherOwnerId;
    @SerializedName("voucher_owner_name")
    private String voucherOwnerName;
    @SerializedName("voucher_order_id")
    private Object voucherOrderId;
    @SerializedName("desc")
    private String desc;

    public Voucher() {
    }

    public static Voucher objectFromData(String str) {

        return new Gson().fromJson(str, Voucher.class);
    }

    public static List<Voucher> arrayVoucherFromData(String str) {

        Type listType = new TypeToken<ArrayList<Voucher>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherTId() {
        return voucherTId;
    }

    public void setVoucherTId(String voucherTId) {
        this.voucherTId = voucherTId;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public String getVoucherDesc() {
        return voucherDesc;
    }

    public void setVoucherDesc(String voucherDesc) {
        this.voucherDesc = voucherDesc;
    }

    public String getVoucherStartDate() {
        return voucherStartDate;
    }

    public void setVoucherStartDate(String voucherStartDate) {
        this.voucherStartDate = voucherStartDate;
    }

    public String getVoucherEndDate() {
        return voucherEndDate;
    }

    public void setVoucherEndDate(String voucherEndDate) {
        this.voucherEndDate = voucherEndDate;
    }

    public String getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(String voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public String getVoucherLimit() {
        return voucherLimit;
    }

    public void setVoucherLimit(String voucherLimit) {
        this.voucherLimit = voucherLimit;
    }

    public String getVoucherStoreId() {
        return voucherStoreId;
    }

    public void setVoucherStoreId(String voucherStoreId) {
        this.voucherStoreId = voucherStoreId;
    }

    public String getVoucherState() {
        return voucherState;
    }

    public void setVoucherState(String voucherState) {
        this.voucherState = voucherState;
    }

    public String getVoucherActiveDate() {
        return voucherActiveDate;
    }

    public void setVoucherActiveDate(String voucherActiveDate) {
        this.voucherActiveDate = voucherActiveDate;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherOwnerId() {
        return voucherOwnerId;
    }

    public void setVoucherOwnerId(String voucherOwnerId) {
        this.voucherOwnerId = voucherOwnerId;
    }

    public String getVoucherOwnerName() {
        return voucherOwnerName;
    }

    public void setVoucherOwnerName(String voucherOwnerName) {
        this.voucherOwnerName = voucherOwnerName;
    }

    public Object getVoucherOrderId() {
        return voucherOrderId;
    }

    public void setVoucherOrderId(Object voucherOrderId) {
        this.voucherOrderId = voucherOrderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
