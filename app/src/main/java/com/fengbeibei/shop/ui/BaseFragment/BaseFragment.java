package com.fengbeibei.shop.ui.BaseFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.BaseFragmentInterface;
import com.fengbeibei.shop.utils.DialogHelper;

/**
 * BaseFragment
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-5 12:09
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {
    private static final String TAG = "BaseFragment";
    protected Dialog mWaitDialog;
    protected LayoutInflater mInflater;
    /**
     * 获取当前布局
     * @return
     */
    protected int getLayoutId(){
        return 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


    /**
     * 扩展布局
     * @param resId
     * @return
     */
    protected View inflaterView(int resId){
        return mInflater.inflate(resId,null);
    }

    @Override
    public void initData() {}
    @Override
    public void initView(View view) { }

    protected Dialog showWaitDialog(){
            if(mWaitDialog == null){
                mWaitDialog = DialogHelper.getDialog(getActivity(), "", R.layout.view_dialog_loading, R.style.Dialog);
            }
            if(mWaitDialog != null){
                mWaitDialog.show();
            }
            return mWaitDialog;
    }

    protected void hideWaitDialog(){
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
