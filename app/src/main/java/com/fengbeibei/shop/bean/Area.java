package com.fengbeibei.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-01 16:37
 */
public class Area {

    /**
     * id : 37
     * name : 东城区
     */

    @SerializedName("area_id")
    private String id;
    @SerializedName("area_name")
    private String name;

    public static Area objectFromData(String str) {

        return new Gson().fromJson(str, Area.class);
    }

    public static List<Area> arrayAreaFromData(String str) {

        Type listType = new TypeToken<ArrayList<Area>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
