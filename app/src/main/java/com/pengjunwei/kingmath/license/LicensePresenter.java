package com.pengjunwei.kingmath.license;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.activity.BaseActivityPresenter;
import com.pengjunwei.kingmath.pojo.SLicense;
import com.pengjunwei.kingmath.pojo.SLicenseListResult;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.RxSubscriber;
import com.pengjunwei.kingmath.user.ILoginPresenter;
import com.pengjunwei.kingmath.user.LoginListener;
import com.pengjunwei.kingmath.user.LoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

/**
 * Created by WikiPeng on 2017/3/20 13:56.
 */
public class LicensePresenter extends BaseActivityPresenter implements ILicensePresenter, LoginListener {

    protected LicenseInteractor.Interactor mInteractor;

    protected int pageSize;
    protected int pageIndex;

    //-----
    protected ILoginPresenter mLoginPresenter;

    public LicensePresenter(Activity activity) {
        super(activity);
        setMVPView(new LicenseView(getProvider(), this));
        initData();
    }


    @Override
    protected void initData() {
        mLoginPresenter = new LoginPresenter(getProvider().fromViewStub(R.id.loginViewStub));
        mLoginPresenter.setLoginListener(this);
        ((ILicenseView) mvpView).setLoginView(((IPresenter) mLoginPresenter).getMVPView());

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
    public void showLicenseList() {
        mInteractor.getList(pageSize, pageIndex * pageSize, "").subscribe(new RxSubscriber<SLicenseListResult>() {
            @Override
            public void onNext(SLicenseListResult result) {
                handleLicenseListResult(result);
            }
        });
    }

    protected void handleLicenseListResult(SLicenseListResult result) {
        if (result == null) {
            result = new SLicenseListResult();
        }
        String showText = "";
        if (result.handleEmpty()) {
            showText = "当前注册码为空";
        } else {
            for (SLicense item : result.licenseList) {
                showText += item.key + "\n";
            }
        }

        ((ILicenseView) mvpView).showLicenseList(true, showText);
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
            public void onNext(SLicenseVerifyResult result) {
                if (result != null) {
                    FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
                }

                handleLicenseListResult(result);
            }
        });
    }

    @Override
    public void onBtnActionClick(String text) {
        if ("math".equalsIgnoreCase(text)) {
            if (mLoginPresenter.isLogin()) {
                showLicenseList();
            } else {
                ((IPresenter) mLoginPresenter).getMVPView().show(true);
            }
        } else {
            verify(text);
        }
    }

    @Override
    public void createLicense(String corporation, String num) {
        boolean isValid = false;
        if (TextUtils.isEmpty(corporation)) {
            provider.showToast("请输入公司名称");
        } else if (TextUtils.isEmpty(num)) {
            provider.showToast("请输入需要生成的个数");
        } else if (!TextUtils.isDigitsOnly(num)) {
            provider.showToast("生成的个数必须要为数字");
        } else {
            isValid = true;
        }

        if (!isValid) {
            return;
        }

        mInteractor.create(corporation, Integer.parseInt(num)).subscribe(new RxSubscriber<SLicenseListResult>() {
            @Override
            public void onNext(SLicenseListResult result) {
                if (result != null) {
                    FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
                }
            }
        });
    }

    @Override
    public void onLogin(boolean isSuccess) {
        ((IPresenter) mLoginPresenter).getMVPView().show(false);
        showLicenseList();
    }
}
