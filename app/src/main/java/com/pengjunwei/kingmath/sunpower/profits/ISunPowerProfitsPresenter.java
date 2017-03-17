package com.pengjunwei.kingmath.sunpower.profits;

import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.sunpower.IViewParamSunPower;

/**
 * Created by WikiPeng on 2017/3/12 22:34.
 */
public interface ISunPowerProfitsPresenter extends IViewParamSunPower {
    void updateSelfUsePercent(float progress);
}
