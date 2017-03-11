package com.pengjunwei.kingmath.model;

/**
 * Created by WikiPeng on 2017/3/11 11:01.
 */
public class FactorInfo {
    /**
     * 参数的名字
     */
    public String name;
    /**
     * 参数的值
     */
    public String value;
    /**
     * 参数的单位
     */
    public String unitText;

    public FactorInfo(String name, String value, String unitText) {
        this.name = name;
        this.value = value;
        this.unitText = unitText;
    }
}
