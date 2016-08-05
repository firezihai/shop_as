package com.fengbeibei.shop.activity;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.BaseViewInterface;
import com.fengbeibei.shop.utils.DialogHelper;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-05 20:00
 */
public class BaseActivity extends FragmentActivity implements BaseViewInterface{
    protected Dialog mWaitDialog;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    private Dialog showWaitDialog(){
        if(mWaitDialog == null){
            mWaitDialog = DialogHelper.getDialog(this, "", R.layout.view_dialog_loading, R.style.Dialog);
        }
        if(mWaitDialog != null){

            mWaitDialog.show();
        }
        return mWaitDialog;
    }

    private void hideWaitDialog(){
        if(mWaitDialog != null){
            try{
                mWaitDialog.dismiss();
                mWaitDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
