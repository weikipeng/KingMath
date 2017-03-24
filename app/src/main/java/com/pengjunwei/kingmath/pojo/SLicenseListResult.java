package com.pengjunwei.kingmath.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/23 13:56.
 */
public class SLicenseListResult extends SBaseResult {

    @SerializedName("res")
    public List<SLicense> licenseList;

    @Override
    public boolean handleEmpty() {
        return licenseList == null || licenseList.size() <= 0;
    }
}
