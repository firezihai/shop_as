package com.zihai.shop.interf;

/**
 * Created by Administrator on 2016/7/22.
 */
public abstract interface Area {
    public static final int TYPE_PROVINCE = 1;
    public static final int TYPE_CITY = 2;
    public static final int TYPE_DISTRICT = 3;
    public abstract String getName();
    public abstract int getType();
}
