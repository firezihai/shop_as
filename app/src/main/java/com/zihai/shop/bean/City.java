package com.zihai.shop.bean;

import com.zihai.shop.interf.Area;

/**
 * City
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 11:34
 */
public class City implements Area {
    private String mName;
    private String mId;
    private Province mProvince;

    public City(Province province, String name, String id) {
        mProvince = province;
        mName = name;
        mId = id;
    }

    public Province getProvince() {
        return mProvince;
    }

    @Override
    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    @Override
    public int getType() {
        return TYPE_CITY;
    }
}
