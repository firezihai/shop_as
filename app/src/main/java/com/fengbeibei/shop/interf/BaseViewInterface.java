package com.fengbeibei.shop.interf;

import android.view.View;

/**
 * Created by zihai on 2016-08-02.
 */
public interface BaseViewInterface {
    /**
     * 初始数据
     */
    void initData();

    /**
     * 初始视图。
     * 在执行onViewCreated事件时调用
     */
    void initView();
}
