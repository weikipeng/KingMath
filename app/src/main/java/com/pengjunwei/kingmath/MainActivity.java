package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pengjunwei.kingmath.sunpower.SunPowerPresenter;

/**
 * Created by WikiPeng on 2017/3/10 14:58.
 */
public class MainActivity extends BaseMVPActivity {
    public static final String EXTRA_DATA = "data";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new SunPowerPresenter(this);
        addLifeCycleListeners(mPresenter);
        mPresenter.refresh(true);
    }
}
