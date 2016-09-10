package com.fengbeibei.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-08 17:26
 */
public class Invoice {

    /**
     * inv_id : 6
     * member_id : 186
     * inv_state : 1
     * inv_title : 个人
     * inv_content : 日用品
     * inv_company :
     * inv_code :
     * inv_reg_addr :
     * inv_reg_phone :
     * inv_reg_bname :
     * inv_reg_baccount :
     * inv_rec_name :
     * inv_rec_mobphone :
     * inv_rec_province :
     * inv_goto_addr :
     * content : 普通发票 个人 日用品
     */

    @SerializedName("inv_id")
    private String invId;
    @SerializedName("member_id")
    private String memberId;
    @SerializedName("inv_state")
    private String invState;
    @SerializedName("inv_title")
    private String invTitle;
    @SerializedName("inv_content")
    private String invContent;
    @SerializedName("inv_company")
    private String invCompany;
    @SerializedName("inv_code")
    private String invCode;
    @SerializedName("inv_reg_addr")
    private String invRegAddr;
    @SerializedName("inv_reg_phone")
    private String invRegPhone;
    @SerializedName("inv_reg_bname")
    private String invRegBname;
    @SerializedName("inv_reg_baccount")
    private String invRegBaccount;
    @SerializedName("inv_rec_name")
    private String invRecName;
    @SerializedName("inv_rec_mobphone")
    private String invRecMobphone;
    @SerializedName("inv_rec_province")
    private String invRecProvince;
    @SerializedName("inv_goto_addr")
    private String invGotoAddr;
    @SerializedName("content")
    private String content;
    public Invoice(){

    }
    public static Invoice objectFromData(String str) {

        return new Gson().fromJson(str, Invoice.class);
    }

    public static List<Invoice> arrayInvoiceFromData(String str) {

        Type listType = new TypeToken<ArrayList<Invoice>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getInvState() {
        return invState;
    }

    public void setInvState(String invState) {
        this.invState = invState;
    }

    public String getInvTitle() {
        return invTitle;
    }

    public void setInvTitle(String invTitle) {
        this.invTitle = invTitle;
    }

    public String getInvContent() {
        return invContent;
    }

    public void setInvContent(String invContent) {
        this.invContent = invContent;
    }

    public String getInvCompany() {
        return invCompany;
    }

    public void setInvCompany(String invCompany) {
        this.invCompany = invCompany;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvRegAddr() {
        return invRegAddr;
    }

    public void setInvRegAddr(String invRegAddr) {
        this.invRegAddr = invRegAddr;
    }

    public String getInvRegPhone() {
        return invRegPhone;
    }

    public void setInvRegPhone(String invRegPhone) {
        this.invRegPhone = invRegPhone;
    }

    public String getInvRegBname() {
        return invRegBname;
    }

    public void setInvRegBname(String invRegBname) {
        this.invRegBname = invRegBname;
    }

    public String getInvRegBaccount() {
        return invRegBaccount;
    }

    public void setInvRegBaccount(String invRegBaccount) {
        this.invRegBaccount = invRegBaccount;
    }

    public String getInvRecName() {
        return invRecName;
    }

    public void setInvRecName(String invRecName) {
        this.invRecName = invRecName;
    }

    public String getInvRecMobphone() {
        return invRecMobphone;
    }

    public void setInvRecMobphone(String invRecMobphone) {
        this.invRecMobphone = invRecMobphone;
    }

    public String getInvRecProvince() {
        return invRecProvince;
    }

    public void setInvRecProvince(String invRecProvince) {
        this.invRecProvince = invRecProvince;
    }

    public String getInvGotoAddr() {
        return invGotoAddr;
    }

    public void setInvGotoAddr(String invGotoAddr) {
        this.invGotoAddr = invGotoAddr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
