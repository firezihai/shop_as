package com.fengbeibei.shop.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengbeibei.shop.R;

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

    public static Dialog gettDialog(Context context,String message,int layout){
        return getDialog(context, message, layout, R.style.myProgressBarLoading);
    }

    public static Dialog getDialog(Context context,String message,int layout,int style){
        View view = LayoutInflater.from(context).inflate(layout,null);
        TextView textView = (TextView) view.findViewById(R.id.loadingText);
        if(!message.isEmpty()) {
            textView.setText(message);
        }else{
            textView.setVisibility(View.GONE);
        }
        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return dialog;
    }
}
