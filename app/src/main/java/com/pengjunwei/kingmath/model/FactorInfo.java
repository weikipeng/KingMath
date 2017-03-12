package com.pengjunwei.kingmath.model;

/**
 * Created by WikiPeng on 2017/3/11 11:01.
 */
public class FactorInfo implements Cloneable{
    public String fieldName;
    /**
     * 参数的名字
     */
    public String name;
    /**
     * 参数的值
     */
    public Object value;
    /**
     * 参数的单位
     */
    public String unitText;

    public FactorInfo(String fieldName, String name, Object value, String unitText) {
        this.fieldName = fieldName;
        this.name = name;
        this.value = value;
        this.unitText = unitText;
    }

    @Override
    public FactorInfo clone() throws CloneNotSupportedException {
        return (FactorInfo) super.clone();
    }
}
