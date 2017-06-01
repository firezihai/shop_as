package com.zihai.shop.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/22.
 */
public class GoodsCommend {

    @SerializedName("goods_id")
    private String goodsId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("goods_promotion_price")
    private String goodsPromotionPrice;
    @SerializedName("goods_image_url")
    private String goodsImageUrl;


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public String getGoodsPromotionPrice() {
        return goodsPromotionPrice;
    }

    public void setGoodsPromotionPrice(String goodsPromotionPrice) {
        this.goodsPromotionPrice = goodsPromotionPrice;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

}
