package com.fengbeibei.shop.bean;

import com.fengbeibei.shop.interf.Area;

/**
 * Province
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 11:30
 */
public class Province implements Area {
    private String mName;
    private String mId;

    public Province(String id, String name) {
        mId = id;
        mName = name;
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
        return TYPE_PROVINCE;
    }
}
