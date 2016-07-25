package com.fengbeibei.shop.bean;

/**
 * Created by Administrator on 2016/7/20.
 */
public class Spec {
    private String specId;
    private String specName;

    public Spec() {
    }

    public Spec(String specId, String specName) {
        this.specId = specId;
        this.specName = specName;
    }

    public String getSpecId() {
        return specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "specId=" + specId +
                ", specName='" + specName + '\'' +
                '}';
    }
}
