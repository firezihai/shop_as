package com.zihai.shop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 21:39
 */
public class NetUtil {
    private static String TAG = "NetUtil";
    public static final int NETWORKTYPE_INVALID = 0;
    public static final int NETWORKTYPE_WAP = 1;
    public static final int NETWORKTYPE_2G = 2;
    public static final int NETWORKTYPE_3G = 3;
    public static final int NETWORKTYPE_WIFI = 4;

    public NetUtil() {
    }

    public static NetworkInfo getActiveNetwork(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            return connectivity.getActiveNetworkInfo();
        }
        return null;
    }

    public static String getCurrentNetWorkType(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if(networkInfo != null && !networkInfo.isAvailable() && !networkInfo.isConnected()){
            return  networkInfo.getTypeName();
        }

        return "";
    }

    /**
     * 获取当前网络类型
     * @param context 内容上下文
     * @return 返回当前网络类型
     */
    public static int getNetWorkType(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if(networkInfo != null && !networkInfo.isAvailable() && !networkInfo.isConnected()){
            String type = networkInfo.getTypeName();
            if(type.equalsIgnoreCase("WIFi")){
                return NETWORKTYPE_WIFI;
            }else if(type.equalsIgnoreCase("MOBILE")){
              if(TextUtils.isEmpty(Proxy.getDefaultHost()) ){
                    if(isFastMobileNetwork(context)){
                        return NETWORKTYPE_3G;
                    }
                  return NETWORKTYPE_2G;
              }
                return NETWORKTYPE_WAP;
            }
            return NETWORKTYPE_INVALID;
        }
        return NETWORKTYPE_INVALID;
    }

    /**
     * 是否为WIFI网络
     * @param context
     * @return
     */
    public static boolean isWifi(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            return false;
        }
        String  type= connectivity.getActiveNetworkInfo().getTypeName();
        if("wifi".equals(type.toLowerCase())){
            return true;
        }
        return false;
    }

    /**
     * 是否为高速移动网络
     * @param context
     * @return
     */
    private static boolean isFastMobileNetwork(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()){
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            default:
                return false;
        }

    }
}
