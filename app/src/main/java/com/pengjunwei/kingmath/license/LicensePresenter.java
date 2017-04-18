package com.pengjunwei.kingmath.license;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.pengjunwei.kingmath.MainActivity;
import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.mvp.activity.BaseActivityPresenter;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.sunpower.SunPowerPresenter;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.RxSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.UnsupportedEncodingException;

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
    public void onBtnActionClick(String text) {
        verify(text);
    }

    @Override
    public boolean verify(final String license) {
        if (TextUtils.isEmpty(license)) {
            Toast.makeText(getProvider().getActivity(), "请输入激活码！", Toast.LENGTH_SHORT).show();
            return false;
        }

        RxPermissions rxPermissions = new RxPermissions(provider.getActivity()); // where this is an Activity instance

        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new RxSubscriber<Boolean>() {

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            verifyWithPermission(license);
                        }
                    }
                });


        return true;
    }


    protected void verifyWithPermission(String license) {
        mInteractor.verify(LicenseDao.getPhoneId(provider.getActivity()), license).subscribe(new RxSubscriber<SLicenseVerifyResult>() {
            @Override
            public void onNext(SLicenseVerifyResult result) {
                if (result != null) {
                    FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
                }

                handleLicenseListResult(result);
            }
        });
    }

    protected void handleLicenseListResult(SLicenseVerifyResult result) {
        if (result == null) {
            result = new SLicenseVerifyResult();
        }

        if (result.errCode!=0) {
            provider.showToast(result.errMsg);
            return;
        }

        String valueText = BaseInteractor.sGson.toJson(result);
        try {
            valueText = Base64.encodeToString(valueText.getBytes("UTF-8"),Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(provider.getActivity());
        sharedPreferences.edit().putString(SunPowerPresenter.KEY_LICENSE,valueText).apply();

        Intent intent = new Intent(provider.getActivity(), MainActivity.class);
        provider.getActivity().startActivity(intent);
        provider.getActivity().finish();
    }

}
