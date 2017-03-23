package com.pengjunwei.kingmath.license;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.pengjunwei.kingmath.mvp.activity.BaseActivityPresenter;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.pojo.SLoginResult;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.RxSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.UnsupportedEncodingException;

import io.reactivex.disposables.Disposable;

/**
 * Created by WikiPeng on 2017/3/20 13:56.
 */
public class LicensePresenter extends BaseActivityPresenter implements ILicensePresenter {

    protected LicenseInteractor.Interactor mInteractor;
    protected SharedPreferences            mSharedPreferences;

    public LicensePresenter(Activity activity) {
        super(activity);
        setMVPView(new LicenseView(getProvider(), this));
        initData();
    }


    @Override
    protected void initData() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(provider.getActivity());
        mInteractor = new LicenseInteractor.WebInteractor();
    }

    @Override
    public void refresh(boolean isForce) {
        super.refresh(isForce);
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

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(provider.getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(provider.getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        mInteractor.login(userName, password).subscribe(new RxSubscriber<SLoginResult>() {

            @SuppressLint("CommitPrefEdits")
            @Override
            public void onNext(SLoginResult result) {
                handleLoginResult(result);
            }
        });
    }

    protected void handleLoginResult(SLoginResult result) {
        if (result != null) {
            FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
            if (!TextUtils.isEmpty(result.res)) {
                mSharedPreferences.edit().putString(BaseInteractor.PARAM_AUTHORIZATION, result.res).apply();
                BaseInteractor.sAuthorization = result.res;
                String deResult = null;
                try {
                    deResult = new String(Base64.decode(result.res, Base64.NO_WRAP), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                FOpenLog.e("deResult===>" + deResult);
                return;
            }
        }

        Toast.makeText(provider.getActivity(), "登录失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLicenseList() {

    }

    protected void verifyWithPermission(String license) {
        TelephonyManager                    tMgr        = (TelephonyManager) provider.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String phoneNumber = tMgr.getLine1Number();
        @SuppressLint("HardwareIds") String imei        = tMgr.getSimSerialNumber();
        @SuppressLint("HardwareIds") String imsi        = tMgr.getSubscriberId();
        @SuppressLint("HardwareIds") String androidId   = Settings.Secure.getString(provider.getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        phoneNumber = androidId + "-" + phoneNumber + "-" + imei + "-" + imsi;

        mInteractor.verify(phoneNumber, license).subscribe(new RxSubscriber<SLicenseVerifyResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SLicenseVerifyResult result) {
                if (result != null) {
                    FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
                }
            }
        });
    }

    public boolean isLogin() {
        return mSharedPreferences.contains(BaseInteractor.PARAM_AUTHORIZATION);
    }

}
