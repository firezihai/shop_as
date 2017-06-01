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
 * Logistics
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-26 14:52
 */
public class Logistics {

    /**
     * express_name : 京东快递
     * shipping_code : 11428020802
     * deliver_info : [{"time":"2015-12-09 11:35:51","context":"货物已到达【广州萝岗分拣中心】","ftime":"2015-12-09 11:35:51"},{"time":"2015-12-09 11:35:51","context":"货物已交付京东快递","ftime":"2015-12-09 11:35:51"},{"time":"2015-12-09 11:38:58","context":"货物已完成分拣，离开【广州萝岗分拣中心】","ftime":"2015-12-09 11:38:58"},{"time":"2015-12-09 16:53:06","context":"货物已到达【佛山分拨中心】","ftime":"2015-12-09 16:53:06"},{"time":"2015-12-09 17:15:47","context":"货物已完成分拣，离开【佛山分拨中心】","ftime":"2015-12-09 17:15:47"},{"time":"2015-12-10 06:48:11","context":"货物已到达【桂城站】","ftime":"2015-12-10 06:48:11"},{"time":"2015-12-10 07:17:59","context":"货物已分配，等待配送","ftime":"2015-12-10 07:17:59"},{"time":"2015-12-10 07:29:13","context":"配送员开始配送，请您准备收货，配送员，古安迅，手机号，18575789453","ftime":"2015-12-10 07:29:13"},{"time":"2015-12-10 11:06:53","context":"货物已完成配送，感谢您选择京东配送","ftime":"2015-12-10 11:06:53"}]
     */

    @SerializedName("express_name")
    private String expressName;
    @SerializedName("shipping_code")
    private String shippingCode;
    /**
     * time : 2015-12-09 11:35:51
     * context : 货物已到达【广州萝岗分拣中心】
     * ftime : 2015-12-09 11:35:51
     */

    @SerializedName("deliver_info")
    private List<DeliverInfoBean> deliverInfo;

    public static Logistics objectFromData(String str) {

        return new Gson().fromJson(str, Logistics.class);
    }

    public static Logistics objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Logistics.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Logistics> arrayLogisticsFromData(String str) {

        Type listType = new TypeToken<ArrayList<Logistics>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Logistics> arrayLogisticsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Logistics>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public List<DeliverInfoBean> getDeliverInfo() {
        return deliverInfo;
    }

    public void setDeliverInfo(List<DeliverInfoBean> deliverInfo) {
        this.deliverInfo = deliverInfo;
    }

    public static class DeliverInfoBean {
        @SerializedName("time")
        private String time;
        @SerializedName("context")
        private String context;
        @SerializedName("ftime")
        private String ftime;

        public static DeliverInfoBean objectFromData(String str) {

            return new Gson().fromJson(str, DeliverInfoBean.class);
        }

        public static DeliverInfoBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DeliverInfoBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DeliverInfoBean> arrayDeliverInfoBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DeliverInfoBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DeliverInfoBean> arrayDeliverInfoBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DeliverInfoBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
