package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pengjunwei.kingmath.sunpower.profits.SunPowerProfitsPresenter;

/**
 * Created by WikiPeng on 2017/3/11 15:23.
 */
public class SunPowerProfitsActivity extends BaseMVPActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_power_profits);
        mPresenter = new SunPowerProfitsPresenter(this);
        mPresenter.setIntent(getIntent());
        mPresenter.refresh(true);
    }
}
