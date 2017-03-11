package com.pengjunwei.kingmath.sunpower.profits;

import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerMVPView;

/**
 * Created by WikiPeng on 2017/3/11 18:34.
 */
public class SunPowerProfitsView extends BaseRecyclerMVPView{

    public SunPowerProfitsView(IMVPProvider provider) {
        super(provider);
    }

    public <T extends IPresenter> SunPowerProfitsView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
    }
}
