package com.pengjunwei.kingmath.license;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.pengjunwei.kingmath.mvp.activity.BaseActivityPresenter;

/**
 * Created by WikiPeng on 2017/3/20 13:56.
 */
public class LicensePresenter extends BaseActivityPresenter implements ILicensePresenter {

    protected LicenseInteractor.Interactor mInteractor;

    public LicensePresenter(Activity activity) {
        super(activity);
        setMVPView(new LicenseView(getProvider(), this));
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

    @Override
    public boolean verify(String license) {
        if (TextUtils.isEmpty(license)) {
            Toast.makeText(getProvider().getActivity(), "请输入激活码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        mInteractor.verify(license);
        return true;
    }
}
