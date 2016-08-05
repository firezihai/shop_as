package com.fengbeibei.shop.ui.BaseFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbeibei.shop.interf.BaseFragmentInterface;
import com.fengbeibei.shop.interf.GoodsFragmentListener;

/**
 * Created by zihai on 2016-08-02.
 */
public abstract class  GoodsBaseFragment extends ViewPagerFragment {
    /**
     * 设置fragment更新
     * @param data
     */
    protected abstract void setUpdate(String data);

}
