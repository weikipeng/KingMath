package com.pengjunwei.kingmath.license;

import android.app.Activity;

import com.pengjunwei.kingmath.mvp.activity.BaseActivityPresenter;

/**
 * Created by WikiPeng on 2017/3/20 13:56.
 */
public class LicensePresenter extends BaseActivityPresenter implements ILicensePresenter {

    protected LicenseInteractor.Interactor mInteractor;

    public LicensePresenter(Activity activity) {
        super(activity);
        initData();
    }


    @Override
    protected void initData() {
        mInteractor = new LicenseInteractor.WebInteractor();
    }

    @Override
    public void refresh(boolean isForce) {
        super.refresh(isForce);
    }
}
