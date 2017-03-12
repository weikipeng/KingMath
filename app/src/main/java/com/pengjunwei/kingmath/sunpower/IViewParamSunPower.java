package com.pengjunwei.kingmath.sunpower;

import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.mvp.BaseViewParam;
import com.pengjunwei.kingmath.mvp.IViewParam;

/**
 * Created by WikiPeng on 2017/3/12 12:04.
 */
public interface IViewParamSunPower extends IViewParam {
    void updateFactor(FactorInfo data);
}
