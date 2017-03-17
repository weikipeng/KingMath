package com.pengjunwei.kingmath.sunpower;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.pengjunwei.kingmath.model.SunPower;

import java.io.UnsupportedEncodingException;

/**
 * Created by WikiPeng on 2017/3/17 16:18.
 */
public class SunPowerDao {
    public static final String KEY_SUN_POWER = "sun_power";
    protected Gson              gson;
    protected SharedPreferences preferences;

    public SunPowerDao(Context context) {
        gson = new Gson();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SunPower getSunPower() {
        SunPower sunPower = null;
        if (preferences.contains(KEY_SUN_POWER)) {
            String text = preferences.getString(KEY_SUN_POWER, "");
            if (!TextUtils.isEmpty(text)) {
                try {
                    text = new String(Base64.decode(text.getBytes("UTF-8"), Base64.NO_WRAP), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sunPower = gson.fromJson(text, SunPower.class);
            }
        }
        if (sunPower == null) {
            sunPower = new SunPower();
        }

        return sunPower;
    }

    public boolean save(SunPower sunPower) {
        if (sunPower == null) {
            return false;
        }

        String result = gson.toJson(sunPower);
        try {
            result = Base64.encodeToString(result.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        preferences.edit().putString(KEY_SUN_POWER, result).apply();
        return true;
    }
}
