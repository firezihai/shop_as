package com.zihai.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Goods
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-13 9:44
 */
public class Goods {

    /**
     * goods_id : 9656
     * store_id : 6
     * goods_name : 蜂贝贝家居 欧式客厅地柜组合 HH-K039-DG 白色 柜体组合
     * goods_price : 6705.00
     * goods_marketprice : 11175.00
     * goods_image : 6_05037722162524784.jpg
     * goods_salenum : 0
     * evaluation_good_star : 5
     * evaluation_count : 0
     * is_virtual : 0
     * is_presell : 0
     * sole_flag : false
     * group_flag : false
     * xianshi_flag : false
     * goods_image_url : http://www.fengbeibei.com/data/upload/shop/store/goods/6/6_05037722162524784_360.jpg
     */

    @SerializedName("goods_id")
    private String goodsId;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("goods_marketprice")
    private String goodsMarketprice;
    @SerializedName("goods_image")
    private String goodsImage;
    @SerializedName("goods_salenum")
    private String goodsSalenum;
    @SerializedName("evaluation_good_star")
    private String evaluationGoodStar;
    @SerializedName("evaluation_count")
    private String evaluationCount;
    @SerializedName("is_virtual")
    private String isVirtual;
    @SerializedName("is_presell")
    private String isPresell;
    @SerializedName("sole_flag")
    private boolean soleFlag;
    @SerializedName("group_flag")
    private boolean groupFlag;
    @SerializedName("xianshi_flag")
    private boolean xianshiFlag;
    @SerializedName("goods_image_url")
    private String goodsImageUrl;


    public static List<Goods> arrayListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<Goods>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsMarketprice() {
        return goodsMarketprice;
    }

    public void setGoodsMarketprice(String goodsMarketprice) {
        this.goodsMarketprice = goodsMarketprice;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(String goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public String getEvaluationGoodStar() {
        return evaluationGoodStar;
    }

    public void setEvaluationGoodStar(String evaluationGoodStar) {
        this.evaluationGoodStar = evaluationGoodStar;
    }

    public String getEvaluationCount() {
        return evaluationCount;
    }

    public void setEvaluationCount(String evaluationCount) {
        this.evaluationCount = evaluationCount;
    }

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(String isPresell) {
        this.isPresell = isPresell;
    }

    public boolean isSoleFlag() {
        return soleFlag;
    }

    public void setSoleFlag(boolean soleFlag) {
        this.soleFlag = soleFlag;
    }

    public boolean isGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public boolean isXianshiFlag() {
        return xianshiFlag;
    }

    public void setXianshiFlag(boolean xianshiFlag) {
        this.xianshiFlag = xianshiFlag;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }
}

