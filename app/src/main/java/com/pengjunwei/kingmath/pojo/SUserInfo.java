package com.pengjunwei.kingmath.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WikiPeng on 2017/3/23 13:39.
 */
public class SUserInfo {
    /**
     * userName : admin
     * key : LL789B8HBMT25H1YYKLTJLMWY89YDTUQ5SP5TMCVZWMV5CJUNVCW6QHUPGJS138W
     * ip : 10.30.30.18
     */

    @SerializedName("userName")
    public String userName;
    @SerializedName("key")
    public String key;
    @SerializedName("ip")
    public String ip;
//    {"userName":"admin","key":"LL789B8HBMT25H1YYKLTJLMWY89YDTUQ5SP5TMCVZWMV5CJUNVCW6QHUPGJS138W","ip":"10.30.30.18"}
}
