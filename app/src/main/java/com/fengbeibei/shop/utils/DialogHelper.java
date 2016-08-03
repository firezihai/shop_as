package com.fengbeibei.shop.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by zihai on 2016-08-03.
 */
public class DialogHelper {
    /***
     * 获取一个耗时等待对话框
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

}
