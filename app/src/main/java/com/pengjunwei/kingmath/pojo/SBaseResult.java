package com.pengjunwei.kingmath.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WikiPeng on 2017/3/21 11:04.
 */
public class SBaseResult {
    /**
     * errCode : -1
     * errMsg : 注册码不存在
     */
    @SerializedName("errCode")
    public int    errCode;
    @SerializedName("errMsg")
    public String errMsg;

    public boolean handleEmpty() {
        return false;
    }
}
