package com.fengbeibei.shop.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;

/**
 * Created by zihai on 2016-08-03.
 */
public class DialogUtil {


    /**
     * 获取一个加载提示框
     * @param context         上下文
     * @param message       提示信息
     * @param layout           布局文件
     * @param style             提示框样式
     * @return  Dialog          返回一个Dialog对象
     */
    public static Dialog getLoadingDialog(Context context,String message,int layout,int style){
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

    public static Dialog confirmDialog(Context context,String title,String content,View.OnClickListener OnConfirmListener,View.OnClickListener onCancelListener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom,null);
        TextView titleView = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView contentView = (TextView) view.findViewById(R.id.tv_dialog_content);
        Button confirm = (Button)view.findViewById(R.id.dialog_confirm_btn);
        Button cancel = (Button) view.findViewById(R.id.dialog_cancel_btn);
        if(title.isEmpty()) {
            titleView.setVisibility(View.GONE);
        }else{
            titleView.setText(title);
        }
        if(content.isEmpty()){
            contentView.setVisibility(View.GONE);
        }else{
            contentView.setText(content);
        }
        confirm.setOnClickListener(OnConfirmListener);
        cancel.setOnClickListener(onCancelListener);
        Dialog dialog = new Dialog(context, R.style.ActivityDialog);
        dialog.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return dialog;
    }
}
