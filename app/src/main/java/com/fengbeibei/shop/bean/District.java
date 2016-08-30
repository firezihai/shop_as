package com.fengbeibei.shop.bean;

import com.fengbeibei.shop.interf.Area;

/**
 * District
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 11:37
 */
public class District implements Area {
    private City mCity;
    private String mName;
    private String mId;

    public District( City city, String name, String id) {
        mCity = city;
        mName = name;
        mId = id;
    }



    public City getCity() {
        return mCity;
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
        return TYPE_DISTRICT;
    }
}
