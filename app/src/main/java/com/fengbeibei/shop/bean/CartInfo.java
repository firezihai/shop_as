package com.fengbeibei.shop.bean;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-08 16:49
 */
public class CartInfo {
    public String mAddressApi;
    public Address mAddress;
    public List<Cart> mCartList;
    public Invoice mInvoiceInfo;
    public String mOrderAmount;
    public String mPredeposit;
    public String mRCBalance;
    public String mFreightHash;
    public String mShowOffPay;
    public String mStoreTotalList;
    public String mVatHash;

    public CartInfo() {
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public void setAddressApi(String addressApi) {
        mAddressApi = addressApi;
    }

    public void setCartList(List<Cart> cartList) {
        mCartList = cartList;
    }

    public void setFreightHash(String freightHash) {
        mFreightHash = freightHash;
    }

    public void setInvoiceInfo(Invoice invoiceInfo) {
        mInvoiceInfo = invoiceInfo;
    }


    public void setOrderAmount(String orderAmount) {
        mOrderAmount = orderAmount;
    }

    public void setPredeposit(String predeposit) {
        mPredeposit = predeposit;
    }

    public void setRCBalance(String RCBalance) {
        mRCBalance = RCBalance;
    }

    public void setShowOffPay(String showOffPay) {
        mShowOffPay = showOffPay;
    }

    public void setStoreTotalList(String storeTotalList) {
        mStoreTotalList = storeTotalList;
    }

    public void setVatHash(String vatHash) {
        mVatHash = vatHash;
    }

    public Address getAddress() {
        return mAddress;
    }

    public String getAddressApi() {
        return mAddressApi;
    }

    public List<Cart> getCartList() {
        return mCartList;
    }

    public String getFreightHash() {
        return mFreightHash;
    }

    public Invoice getInvoiceInfo() {
        return mInvoiceInfo;
    }

    public String getOrderAmount() {
        return mOrderAmount;
    }

    public String getPredeposit() {
        return mPredeposit;
    }

    public String getRCBalance() {
        return mRCBalance;
    }

    public String getShowOffPay() {
        return mShowOffPay;
    }

    public String getStoreTotalList() {
        return mStoreTotalList;
    }

    public String getVatHash() {
        return mVatHash;
    }
}
