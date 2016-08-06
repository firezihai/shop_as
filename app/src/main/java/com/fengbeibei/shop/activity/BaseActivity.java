package com.fengbeibei.shop.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.BaseViewInterface;
import com.fengbeibei.shop.utils.DialogUtil;

import butterknife.ButterKnife;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-05 20:00
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseViewInterface {
    protected Dialog mLoadingDialog;
    protected LayoutInflater mInflater;
    /**
     * 加载提示布局
     */
    protected int mLoadingLayout = R.layout.view_dialog_loading;
    /**
     * 加载提示样式
     */
    protected int mLoadingStyle = R.style.ContentOverlay;
    /**
     * 获取当前布局
     * @return
     */
    protected  int getLayoutId(){
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        mInflater = getLayoutInflater();
        ButterKnife.bind(this);
        init(savedInstanceState);
        initView();
    }
    public void init(Bundle savedInstancedState){

    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }
    /**
     * 扩展布局
     * @param resId
     * @return
     */
    protected View inflaterView(int resId){
        return mInflater.inflate(resId,null);
    }

    /**
     * 设置内容布局之前调用
     */
    protected void onBeforeSetContentLayout(){}
    protected void showLoadingDialog(){
        getLoadingDialog(getString(R.string.loading), mLoadingLayout, mLoadingStyle);
    }
    protected void showLoadingDialog(String message){
        getLoadingDialog(message, mLoadingLayout, mLoadingStyle);
    }

    private Dialog getLoadingDialog(String message,int resLayout,int resStyle){
        if(mLoadingDialog == null){
            mLoadingDialog = DialogUtil.getLoadingDialog(this, message, resLayout, resStyle);
        }
        if(mLoadingDialog != null){
            mLoadingDialog.show();
        }
        return mLoadingDialog;
    }

    protected void hideLoadingDialog(){
        if(mLoadingDialog != null){
            try{
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
