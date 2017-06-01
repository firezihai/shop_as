package com.zihai.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-03 22:10
 */
public class Cart {



    @SerializedName("store_id")
    private String storeId;
    @SerializedName("store_name")
    private String storeName;


    @SerializedName("goods")
    private List<Goods> goods;

    private boolean checked = true;
    private String freight;

    private String storeGoodsTotal;

    private String storeMansongRuleList;

    private String storeVoucherInfo;

    private List<Voucher> storeVoucherList;


    public static Cart objectFromData(String str) {

        return new Gson().fromJson(str, Cart.class);
    }

    public static List<Cart> arrayCartFromData(String str) {

        Type listType = new TypeToken<ArrayList<Cart>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
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

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public void setStoreGoodsTotal(String storeGoodsTotal) {
        this.storeGoodsTotal = storeGoodsTotal;
    }
    public void setStoreVoucherList(List<Voucher> storeVoucherList) {
        this.storeVoucherList = storeVoucherList;
    }
    public String getFreight() {
        return freight;
    }

    public String getStoreGoodsTotal() {
        return storeGoodsTotal;
    }

    public List<Voucher> getStoreVoucherList() {
        return storeVoucherList;
    }

    public void setStoreVoucherInfo(String storeVoucherInfo) {
        this.storeVoucherInfo = storeVoucherInfo;
    }

    public String getStoreVoucherInfo() {
        return storeVoucherInfo;
    }

    public static class Goods {
        @SerializedName("cart_id")
        private String cartId;
        @SerializedName("buyer_id")
        private String buyerId;
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("store_name")
        private String storeName;
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
        @SerializedName("bl_id")
        private String blId;
        @SerializedName("state")
        private boolean state;
        @SerializedName("storage_state")
        private boolean storageState;
        @SerializedName("goods_commonid")
        private String goodsCommonid;
        @SerializedName("gc_id")
        private String gcId;
        @SerializedName("goods_serial")
        private String goodsSerial;
        @SerializedName("transport_id")
        private String transportId;
        @SerializedName("goods_freight")
        private String goodsFreight;
        @SerializedName("goods_vat")
        private String goodsVat;
        @SerializedName("goods_storage")
        private String goodsStorage;
        @SerializedName("goods_storage_alarm")
        private String goodsStorageAlarm;
        @SerializedName("is_fcode")
        private String isFcode;
        @SerializedName("have_gift")
        private String haveGift;
        @SerializedName("groupbuy_info")
        private Object groupbuyInfo;
        @SerializedName("xianshi_info")
        private Object xianshiInfo;
        @SerializedName("is_book")
        private String isBook;
        @SerializedName("book_down_payment")
        private String bookDownPayment;
        @SerializedName("book_final_payment")
        private String bookFinalPayment;
        @SerializedName("book_down_time")
        private String bookDownTime;
        @SerializedName("is_chain")
        private String isChain;
        @SerializedName("goods_image_url")
        private String goodsImageUrl;
        @SerializedName("goods_sum")
        private String goodsSum;
        @SerializedName("sole_info")
        private List<?> soleInfo;

        private boolean checked = true;
        public static Goods objectFromData(String str) {

            return new Gson().fromJson(str, Goods.class);
        }

        public static List<Goods> arrayGoodsFromData(String str) {

            Type listType = new TypeToken<ArrayList<Goods>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
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

        public String getBlId() {
            return blId;
        }

        public void setBlId(String blId) {
            this.blId = blId;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public boolean isStorageState() {
            return storageState;
        }

        public void setStorageState(boolean storageState) {
            this.storageState = storageState;
        }

        public String getGoodsCommonid() {
            return goodsCommonid;
        }

        public void setGoodsCommonid(String goodsCommonid) {
            this.goodsCommonid = goodsCommonid;
        }

        public String getGcId() {
            return gcId;
        }

        public void setGcId(String gcId) {
            this.gcId = gcId;
        }

        public String getGoodsSerial() {
            return goodsSerial;
        }

        public void setGoodsSerial(String goodsSerial) {
            this.goodsSerial = goodsSerial;
        }

        public String getTransportId() {
            return transportId;
        }

        public void setTransportId(String transportId) {
            this.transportId = transportId;
        }

        public String getGoodsFreight() {
            return goodsFreight;
        }

        public void setGoodsFreight(String goodsFreight) {
            this.goodsFreight = goodsFreight;
        }

        public String getGoodsVat() {
            return goodsVat;
        }

        public void setGoodsVat(String goodsVat) {
            this.goodsVat = goodsVat;
        }

        public String getGoodsStorage() {
            return goodsStorage;
        }

        public void setGoodsStorage(String goodsStorage) {
            this.goodsStorage = goodsStorage;
        }

        public String getGoodsStorageAlarm() {
            return goodsStorageAlarm;
        }

        public void setGoodsStorageAlarm(String goodsStorageAlarm) {
            this.goodsStorageAlarm = goodsStorageAlarm;
        }

        public String getIsFcode() {
            return isFcode;
        }

        public void setIsFcode(String isFcode) {
            this.isFcode = isFcode;
        }

        public String getHaveGift() {
            return haveGift;
        }

        public void setHaveGift(String haveGift) {
            this.haveGift = haveGift;
        }

        public Object getGroupbuyInfo() {
            return groupbuyInfo;
        }

        public void setGroupbuyInfo(Object groupbuyInfo) {
            this.groupbuyInfo = groupbuyInfo;
        }

        public Object getXianshiInfo() {
            return xianshiInfo;
        }

        public void setXianshiInfo(Object xianshiInfo) {
            this.xianshiInfo = xianshiInfo;
        }

        public String getIsBook() {
            return isBook;
        }

        public void setIsBook(String isBook) {
            this.isBook = isBook;
        }

        public String getBookDownPayment() {
            return bookDownPayment;
        }

        public void setBookDownPayment(String bookDownPayment) {
            this.bookDownPayment = bookDownPayment;
        }

        public String getBookFinalPayment() {
            return bookFinalPayment;
        }

        public void setBookFinalPayment(String bookFinalPayment) {
            this.bookFinalPayment = bookFinalPayment;
        }

        public String getBookDownTime() {
            return bookDownTime;
        }

        public void setBookDownTime(String bookDownTime) {
            this.bookDownTime = bookDownTime;
        }

        public String getIsChain() {
            return isChain;
        }

        public void setIsChain(String isChain) {
            this.isChain = isChain;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }

        public String getGoodsSum() {
            return goodsSum;
        }

        public void setGoodsSum(String goodsSum) {
            this.goodsSum = goodsSum;
        }

        public List<?> getSoleInfo() {
            return soleInfo;
        }

        public void setSoleInfo(List<?> soleInfo) {
            this.soleInfo = soleInfo;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isChecked() {
            return checked;
        }
    }
}
