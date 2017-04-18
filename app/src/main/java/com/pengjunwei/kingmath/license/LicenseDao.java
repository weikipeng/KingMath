package com.pengjunwei.kingmath.license;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by WikiPeng on 2017/4/18 16:09.
 */
public class LicenseDao {
    public static String getPhoneId(Context context){
        TelephonyManager                    tMgr        = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String phoneNumber = tMgr.getLine1Number();
        @SuppressLint("HardwareIds") String imei        = tMgr.getSimSerialNumber();
        @SuppressLint("HardwareIds") String imsi        = tMgr.getSubscriberId();
        @SuppressLint("HardwareIds") String androidId   = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        phoneNumber = androidId + "-" + phoneNumber + "-" + imei + "-" + imsi;
        return phoneNumber;
    }
}
