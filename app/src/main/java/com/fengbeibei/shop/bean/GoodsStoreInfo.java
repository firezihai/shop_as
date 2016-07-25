package com.fengbeibei.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class GoodsStoreInfo {


    /**
     * store_id : 6
     * store_name : 蜂贝贝家居自营店
     * member_id : 66
     * store_qq : 322723112
     * store_ww : 552222
     * store_phone : 18870889397
     * member_name : adminziying
     * avatar : http://192.168.1.125/data/upload/shop/common/default_user_portrait.gif
     * store_credit : {"store_desccredit":{"text":"描述","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"},"store_servicecredit":{"text":"服务","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"},"store_deliverycredit":{"text":"物流","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"}}
     */

    @SerializedName("store_id")
    private String storeId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("member_id")
    private String memberId;
    @SerializedName("store_qq")
    private String storeQq;
    @SerializedName("store_ww")
    private String storeWw;
    @SerializedName("store_phone")
    private String storePhone;
    @SerializedName("member_name")
    private String memberName;
    @SerializedName("avatar")
    private String avatar;
    /**
     * store_desccredit : {"text":"描述","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"}
     * store_servicecredit : {"text":"服务","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"}
     * store_deliverycredit : {"text":"物流","credit":5,"percent":"----","percent_class":"equal","percent_text":"平"}
     */

    @SerializedName("store_credit")
    private StoreCreditBean storeCredit;

    public static GoodsStoreInfo objectFromData(String str) {

        return new Gson().fromJson(str, GoodsStoreInfo.class);
    }

    public static GoodsStoreInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GoodsStoreInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GoodsStoreInfo> arrayGoodsStoreInfoFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodsStoreInfo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GoodsStoreInfo> arrayGoodsStoreInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GoodsStoreInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStoreQq() {
        return storeQq;
    }

    public void setStoreQq(String storeQq) {
        this.storeQq = storeQq;
    }

    public String getStoreWw() {
        return storeWw;
    }

    public void setStoreWw(String storeWw) {
        this.storeWw = storeWw;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public StoreCreditBean getStoreCredit() {
        return storeCredit;
    }

    public void setStoreCredit(StoreCreditBean storeCredit) {
        this.storeCredit = storeCredit;
    }

    public static class StoreCreditBean {
        /**
         * text : 描述
         * credit : 5
         * percent : ----
         * percent_class : equal
         * percent_text : 平
         */

        @SerializedName("store_desccredit")
        private StoreDesccreditBean storeDesccredit;
        /**
         * text : 服务
         * credit : 5
         * percent : ----
         * percent_class : equal
         * percent_text : 平
         */

        @SerializedName("store_servicecredit")
        private StoreServicecreditBean storeServicecredit;
        /**
         * text : 物流
         * credit : 5
         * percent : ----
         * percent_class : equal
         * percent_text : 平
         */

        @SerializedName("store_deliverycredit")
        private StoreDeliverycreditBean storeDeliverycredit;

        public static StoreCreditBean objectFromData(String str) {

            return new Gson().fromJson(str, StoreCreditBean.class);
        }

        public static StoreCreditBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), StoreCreditBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<StoreCreditBean> arrayStoreCreditBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<StoreCreditBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<StoreCreditBean> arrayStoreCreditBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<StoreCreditBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public StoreDesccreditBean getStoreDesccredit() {
            return storeDesccredit;
        }

        public void setStoreDesccredit(StoreDesccreditBean storeDesccredit) {
            this.storeDesccredit = storeDesccredit;
        }

        public StoreServicecreditBean getStoreServicecredit() {
            return storeServicecredit;
        }

        public void setStoreServicecredit(StoreServicecreditBean storeServicecredit) {
            this.storeServicecredit = storeServicecredit;
        }

        public StoreDeliverycreditBean getStoreDeliverycredit() {
            return storeDeliverycredit;
        }

        public void setStoreDeliverycredit(StoreDeliverycreditBean storeDeliverycredit) {
            this.storeDeliverycredit = storeDeliverycredit;
        }

        public static class StoreDesccreditBean {
            @SerializedName("text")
            private String text;
            @SerializedName("credit")
            private int credit;
            @SerializedName("percent")
            private String percent;
            @SerializedName("percent_class")
            private String percentClass;
            @SerializedName("percent_text")
            private String percentText;

            public static StoreDesccreditBean objectFromData(String str) {

                return new Gson().fromJson(str, StoreDesccreditBean.class);
            }

            public static StoreDesccreditBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), StoreDesccreditBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<StoreDesccreditBean> arrayStoreDesccreditBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<StoreDesccreditBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<StoreDesccreditBean> arrayStoreDesccreditBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<StoreDesccreditBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getPercentClass() {
                return percentClass;
            }

            public void setPercentClass(String percentClass) {
                this.percentClass = percentClass;
            }

            public String getPercentText() {
                return percentText;
            }

            public void setPercentText(String percentText) {
                this.percentText = percentText;
            }
        }

        public static class StoreServicecreditBean {
            @SerializedName("text")
            private String text;
            @SerializedName("credit")
            private int credit;
            @SerializedName("percent")
            private String percent;
            @SerializedName("percent_class")
            private String percentClass;
            @SerializedName("percent_text")
            private String percentText;

            public static StoreServicecreditBean objectFromData(String str) {

                return new Gson().fromJson(str, StoreServicecreditBean.class);
            }

            public static StoreServicecreditBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), StoreServicecreditBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<StoreServicecreditBean> arrayStoreServicecreditBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<StoreServicecreditBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<StoreServicecreditBean> arrayStoreServicecreditBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<StoreServicecreditBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getPercentClass() {
                return percentClass;
            }

            public void setPercentClass(String percentClass) {
                this.percentClass = percentClass;
            }

            public String getPercentText() {
                return percentText;
            }

            public void setPercentText(String percentText) {
                this.percentText = percentText;
            }
        }

        public static class StoreDeliverycreditBean {
            @SerializedName("text")
            private String text;
            @SerializedName("credit")
            private int credit;
            @SerializedName("percent")
            private String percent;
            @SerializedName("percent_class")
            private String percentClass;
            @SerializedName("percent_text")
            private String percentText;

            public static StoreDeliverycreditBean objectFromData(String str) {

                return new Gson().fromJson(str, StoreDeliverycreditBean.class);
            }

            public static StoreDeliverycreditBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), StoreDeliverycreditBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<StoreDeliverycreditBean> arrayStoreDeliverycreditBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<StoreDeliverycreditBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<StoreDeliverycreditBean> arrayStoreDeliverycreditBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<StoreDeliverycreditBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getPercentClass() {
                return percentClass;
            }

            public void setPercentClass(String percentClass) {
                this.percentClass = percentClass;
            }

            public String getPercentText() {
                return percentText;
            }

            public void setPercentText(String percentText) {
                this.percentText = percentText;
            }
        }
    }
}
