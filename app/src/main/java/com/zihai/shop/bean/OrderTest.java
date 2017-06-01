package com.zihai.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderTest
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-24 15:51
 */
public class OrderTest {

    /**
     * order_id : 180
     * order_sn : 7000000000020901
     * pay_sn : 450504813059112186
     * store_id : 6
     * store_name : 蜂贝贝家居自营店
     * buyer_id : 186
     * buyer_name : 段指
     * buyer_email : zihaidetiandi@sina.com
     * buyer_phone : 18870889397
     * add_time : 1451469057
     * payment_code : chain
     * payment_time : 0
     * finnshed_time : 0
     * goods_amount : 7950.00
     * order_amount : 9302.00
     * rcb_amount : 0.00
     * pd_amount : 0.00
     * shipping_fee : 1352.00
     * evaluation_state : 0
     * evaluation_again_state : 0
     * order_state : 20
     * refund_state : 0
     * promotion_id : 0
     * lock_state : 0
     * delete_state : 0
     * refund_amount : 0.00
     * delay_time : 0
     * order_from : 2
     * shipping_code :
     * order_type : 3
     * api_pay_time : 0
     * chain_id : 1
     * chain_code : 894146
     * rpt_amount : 0.00
     * trade_no :
     * state_desc : 待发货
     * payment_name : 到店付款
     * extend_order_goods : [{"rec_id":"270","order_id":"180","goods_id":"9987","goods_name":"蜂贝贝家居 美式乡村卧室套装 BMXW-881-TZ 象牙白 全套组合","goods_price":"7950.00","goods_num":"1","goods_image":"05046300535680393.jpg","goods_pay_price":"7950.00","store_id":"6","buyer_id":"186","goods_type":"1","promotions_id":"0","commis_rate":"0","gc_id":"266","goods_spec":null,"goods_serial":"","goods_image_url":"http://www.fengbeibei.com/data/upload/shop/common/default_goods_image_240.gif"}]
     * if_cancel : false
     * if_receive : false
     * if_lock : false
     * if_deliver : false
     */
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("order_sn")
    private String orderSn;
    @SerializedName("pay_sn")
    private String paySn;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("buyer_id")
    private String buyerId;
    @SerializedName("buyer_name")
    private String buyerName;
    @SerializedName("buyer_email")
    private String buyerEmail;
    @SerializedName("buyer_phone")
    private String buyerPhone;
    @SerializedName("add_time")
    private String addTime;
    @SerializedName("payment_code")
    private String paymentCode;
    @SerializedName("payment_time")
    private String paymentTime;
    @SerializedName("finnshed_time")
    private String finnshedTime;
    @SerializedName("goods_amount")
    private String goodsAmount;
    @SerializedName("order_amount")
    private String orderAmount;
    @SerializedName("rcb_amount")
    private String rcbAmount;
    @SerializedName("pd_amount")
    private String pdAmount;
    @SerializedName("shipping_fee")
    private String shippingFee;
    @SerializedName("evaluation_state")
    private String evaluationState;
    @SerializedName("evaluation_again_state")
    private String evaluationAgainState;
    @SerializedName("order_state")
    private String orderState;
    @SerializedName("refund_state")
    private String refundState;
    @SerializedName("promotion_id")
    private String promotionId;
    @SerializedName("lock_state")
    private String lockState;
    @SerializedName("delete_state")
    private String deleteState;
    @SerializedName("refund_amount")
    private String refundAmount;
    @SerializedName("delay_time")
    private String delayTime;
    @SerializedName("order_from")
    private String orderFrom;
    @SerializedName("shipping_code")
    private String shippingCode;
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("api_pay_time")
    private String apiPayTime;
    @SerializedName("chain_id")
    private String chainId;
    @SerializedName("chain_code")
    private String chainCode;
    @SerializedName("rpt_amount")
    private String rptAmount;
    @SerializedName("trade_no")
    private String tradeNo;
    @SerializedName("state_desc")
    private String stateDesc;
    @SerializedName("payment_name")
    private String paymentName;
    @SerializedName("if_cancel")
    private boolean ifCancel;
    @SerializedName("if_receive")
    private boolean ifReceive;
    @SerializedName("if_lock")
    private boolean ifLock;
    @SerializedName("if_deliver")
    private boolean ifDeliver;
    /**
     * rec_id : 270
     * order_id : 180
     * goods_id : 9987
     * goods_name : 蜂贝贝家居 美式乡村卧室套装 BMXW-881-TZ 象牙白 全套组合
     * goods_price : 7950.00
     * goods_num : 1
     * goods_image : 05046300535680393.jpg
     * goods_pay_price : 7950.00
     * store_id : 6
     * buyer_id : 186
     * goods_type : 1
     * promotions_id : 0
     * commis_rate : 0
     * gc_id : 266
     * goods_spec : null
     * goods_serial :
     * goods_image_url : http://www.fengbeibei.com/data/upload/shop/common/default_goods_image_240.gif
     */

    @SerializedName("extend_order_goods")
    private List<ExtendOrderGoodsBean> extendOrderGoods;

    public static OrderTest objectFromData(String str) {

        return new Gson().fromJson(str, OrderTest.class);
    }

    public static OrderTest objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), OrderTest.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<OrderTest> arrayOrderListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<OrderTest>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<OrderTest> arrayOrderListBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<OrderTest>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getFinnshedTime() {
        return finnshedTime;
    }

    public void setFinnshedTime(String finnshedTime) {
        this.finnshedTime = finnshedTime;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getRcbAmount() {
        return rcbAmount;
    }

    public void setRcbAmount(String rcbAmount) {
        this.rcbAmount = rcbAmount;
    }

    public String getPdAmount() {
        return pdAmount;
    }

    public void setPdAmount(String pdAmount) {
        this.pdAmount = pdAmount;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(String evaluationState) {
        this.evaluationState = evaluationState;
    }

    public String getEvaluationAgainState() {
        return evaluationAgainState;
    }

    public void setEvaluationAgainState(String evaluationAgainState) {
        this.evaluationAgainState = evaluationAgainState;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getRefundState() {
        return refundState;
    }

    public void setRefundState(String refundState) {
        this.refundState = refundState;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getApiPayTime() {
        return apiPayTime;
    }

    public void setApiPayTime(String apiPayTime) {
        this.apiPayTime = apiPayTime;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getRptAmount() {
        return rptAmount;
    }

    public void setRptAmount(String rptAmount) {
        this.rptAmount = rptAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public boolean isIfCancel() {
        return ifCancel;
    }

    public void setIfCancel(boolean ifCancel) {
        this.ifCancel = ifCancel;
    }

    public boolean isIfReceive() {
        return ifReceive;
    }

    public void setIfReceive(boolean ifReceive) {
        this.ifReceive = ifReceive;
    }

    public boolean isIfLock() {
        return ifLock;
    }

    public void setIfLock(boolean ifLock) {
        this.ifLock = ifLock;
    }

    public boolean isIfDeliver() {
        return ifDeliver;
    }

    public void setIfDeliver(boolean ifDeliver) {
        this.ifDeliver = ifDeliver;
    }

    public List<ExtendOrderGoodsBean> getExtendOrderGoods() {
        return extendOrderGoods;
    }

    public void setExtendOrderGoods(List<ExtendOrderGoodsBean> extendOrderGoods) {
        this.extendOrderGoods = extendOrderGoods;
    }

    public static class ExtendOrderGoodsBean {
        @SerializedName("rec_id")
        private String recId;
        @SerializedName("order_id")
        private String orderId;
        @SerializedName("goods_id")
        private String goodsId;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("goods_price")
        private String goodsPrice;
        @SerializedName("goods_num")
        private String goodsNum;
        @SerializedName("goods_image")
        private String goodsImage;
        @SerializedName("goods_pay_price")
        private String goodsPayPrice;
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("buyer_id")
        private String buyerId;
        @SerializedName("goods_type")
        private String goodsType;
        @SerializedName("promotions_id")
        private String promotionsId;
        @SerializedName("commis_rate")
        private String commisRate;
        @SerializedName("gc_id")
        private String gcId;
        @SerializedName("goods_spec")
        private Object goodsSpec;
        @SerializedName("goods_serial")
        private String goodsSerial;
        @SerializedName("goods_image_url")
        private String goodsImageUrl;

        public static ExtendOrderGoodsBean objectFromData(String str) {

            return new Gson().fromJson(str, ExtendOrderGoodsBean.class);
        }

        public static ExtendOrderGoodsBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ExtendOrderGoodsBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ExtendOrderGoodsBean> arrayExtendOrderGoodsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ExtendOrderGoodsBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<ExtendOrderGoodsBean> arrayExtendOrderGoodsBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ExtendOrderGoodsBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getRecId() {
            return recId;
        }

        public void setRecId(String recId) {
            this.recId = recId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

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

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getGoodsPayPrice() {
            return goodsPayPrice;
        }

        public void setGoodsPayPrice(String goodsPayPrice) {
            this.goodsPayPrice = goodsPayPrice;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getPromotionsId() {
            return promotionsId;
        }

        public void setPromotionsId(String promotionsId) {
            this.promotionsId = promotionsId;
        }

        public String getCommisRate() {
            return commisRate;
        }

        public void setCommisRate(String commisRate) {
            this.commisRate = commisRate;
        }

        public String getGcId() {
            return gcId;
        }

        public void setGcId(String gcId) {
            this.gcId = gcId;
        }

        public Object getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(Object goodsSpec) {
            this.goodsSpec = goodsSpec;
        }

        public String getGoodsSerial() {
            return goodsSerial;
        }

        public void setGoodsSerial(String goodsSerial) {
            this.goodsSerial = goodsSerial;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }
    }
}
