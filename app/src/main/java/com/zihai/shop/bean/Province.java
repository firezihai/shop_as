package com.zihai.shop.bean;

import com.zihai.shop.interf.Area;

/**
 * Province
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 11:30
 */
public class Province implements Area {

    private String mName;
    private String mId;


    public Province(String name,String id) {
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
