package com.fengbeibei.shop.interf;

import android.widget.ImageView;

/**
 * SearchHeaderListener
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-10 12:10
 */
public abstract interface SearchHeaderInterface {
    /**
     * 点击返回时调用
     */
    public abstract void onBack();

    /**
     * 网格视图和列表视图切换
     */
    public abstract void onSwitchView(ImageView imageViews);

    /**
     * 点击搜索框时回调
     */
    public abstract void onSearch();
}
