package com.fengbeibei.shop.fragment.Base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.interf.BaseViewInterface;
import com.fengbeibei.shop.utils.DialogUtil;

import butterknife.ButterKnife;

/**
 * BaseFragment
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-5 12:09
 */
public abstract class BaseFragment extends Fragment implements BaseViewInterface {
    private static final String TAG = "BaseFragment";
    protected Dialog mWaitDialog;
    protected LayoutInflater mInflater;
    protected boolean mAddHead = false;
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
    protected int getLayoutId(){
        return 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        onBeforeSetContentLayout();
        if(!isAddHead()) {
            return inflater.inflate(getLayoutId(), container, false);
        }else{
            LinearLayout linearLayout = (LinearLayout) inflaterView(R.layout.activity_common);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.head_height));
            mHeadLayout = (ViewGroup)inflaterView(R.layout.comm_head);
            linearLayout.addView(mHeadLayout,layoutParams);
            linearLayout.addView(inflaterView(getLayoutId()), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return linearLayout;
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    protected void onBeforeSetContentLayout(){}
    public void setAddHead(boolean addHead) {
        mAddHead = addHead;
    }

    public boolean isAddHead() {
        return mAddHead;
    }

    public void setHeadTitle(int resId){
        ((TextView) mHeadLayout.findViewById(R.id.tv_headTitle)).setText(resId);
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
    public void initView() { }

    protected void showLoadingDialog(){
        getLoadingDialog(getString(R.string.loading), mLoadingLayout, mLoadingStyle);
    }
    protected void showLoadingDialog(String message){
        getLoadingDialog(message, mLoadingLayout, mLoadingStyle);
    }

    private Dialog getLoadingDialog(String message,int resLayout,int resStyle){
        if(mWaitDialog == null){
            mWaitDialog = DialogUtil.getLoadingDialog(getActivity(), message, resLayout, resStyle);
        }
        if(mWaitDialog != null){
            mWaitDialog.show();
        }
        return mWaitDialog;
    }

    protected void hideLoadingDialog(){
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
