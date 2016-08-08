package com.fengbeibei.shop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-08 21:39
 */
public class NetUtil {
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
        if(connectivity != null){
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if(networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()){
                return "unknown";
            }
            if(networkInfo.getType() != 1){

            }
        }
    }

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
}
