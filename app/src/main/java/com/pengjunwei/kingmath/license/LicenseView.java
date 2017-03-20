package com.pengjunwei.kingmath.license;

import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;

/**
 * Created by WikiPeng on 2017/3/20 14:05.
 */
public class LicenseView extends BaseMVPView {

    public LicenseView(IMVPProvider provider) {
        super(provider);
    }

    public <T extends IPresenter> LicenseView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
    }
}
