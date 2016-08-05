package com.fengbeibei.shop.ui.BaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * ViewPagerFragment
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016/8/5 14:00
 */
public abstract class ViewPagerFragment extends BaseFragment{
    /**
     * fragment是否可见,默认为false。
     */
    protected boolean mVisible;
    /**
     * 是否启用延迟加载，默认为false。
     */
    protected boolean mDelayLoad = false;

    private boolean mWaitUserVisible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mInflater = inflater;
        View view = inflater.inflate(getLayoutId(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getUserVisibleHint()){
            Fragment parentFragment = getParentFragment();
            if(parentFragment != null && !parentFragment.getUserVisibleHint()){
                super.setUserVisibleHint(false);
                mWaitUserVisible = true;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getActivity() != null){
            List<Fragment> childFragments = getChildFragmentManager().getFragments();
            if(isVisibleToUser){
                if(childFragments != null && childFragments.size()>0){
                    for (Fragment childFragment : childFragments){
                        if(childFragment instanceof ViewPagerFragment){
                            ViewPagerFragment fragment = (ViewPagerFragment) childFragment;
                            fragment.setWaitUserVisible(false);
                            fragment.setUserVisibleHint(true);
                        }
                    }
                }
            }
        }
        if(getUserVisibleHint()){
            mVisible = true;
            onVisible();
        }else{
            mVisible = false;
        }
    }
    /**
     * 当fragment可见时调用,默认实现lazyLoad方法。
     */
    protected void onVisible(){
        lazyLoad();
    }
    /**
     * onVisible执行时调用。
     *为了避免数据在fragment不可见时被创建或初始化，可以将相关逻辑置于lazyLoad方法中。
     */
    protected abstract void lazyLoad();

    protected void setWaitUserVisible(boolean isWaitUserVisible){
        mWaitUserVisible = isWaitUserVisible;
    }
    protected boolean getWaitUserVisible(){
        return mWaitUserVisible;
    }
}
