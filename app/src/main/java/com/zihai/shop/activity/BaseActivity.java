package com.zihai.shop.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.interf.BaseViewInterface;
import com.zihai.shop.utils.DialogUtil;

import butterknife.ButterKnife;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-05 20:00
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseViewInterface {
    protected Dialog mLoadingDialog;
    protected LayoutInflater mInflater;
    protected ViewGroup mHeadLayout;
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
    protected String mKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(getLayoutId());
        mKey = getUserKey();
        mInflater = getLayoutInflater();
        onBeforeSetContentLayout();
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
     * 创建内容布局
     * @param isAddHead 是否添加标头
     */
    protected void createContentView(boolean isAddHead){
        if(isAddHead){
            LinearLayout linearLayout = (LinearLayout) inflaterView(R.layout.activity_common);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.head_height));
            mHeadLayout = (ViewGroup)inflaterView(R.layout.comm_head);
            linearLayout.addView(mHeadLayout,layoutParams);
            linearLayout.addView(inflaterView(getLayoutId()), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            setContentView(linearLayout);
        }else{
            setContentView(getLayoutId());
        }
    }

    protected void setHeadTitle(int resId){
        ((TextView) mHeadLayout.findViewById(R.id.tv_headTitle)).setText(resId);
    }

    protected void setHeadBackBtnListener(View.OnClickListener onClickListener){
        mHeadLayout.findViewById(R.id.back).setOnClickListener(onClickListener);
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
    protected void onBeforeSetContentLayout(){createContentView(false);}
    protected void showLoadingDialog(){
        getLoadingDialog(getString(R.string.loading), mLoadingLayout, mLoadingStyle);
    }
    protected void showLoadingDialog(String message){
        getLoadingDialog(message, mLoadingLayout, mLoadingStyle);
    }
    protected void showLoadingDialog(String message,int loadingLayout,int loadingStyle){
        getLoadingDialog(message, loadingLayout, loadingStyle);
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

    private String getUserKey(){
        String key = MyApplication.getInstance().getLoginKey();
        if(key != null && !key.equals("")){
            return key;
        }else{
            return "";
        }
    }
}
