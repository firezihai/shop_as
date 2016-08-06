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
}
