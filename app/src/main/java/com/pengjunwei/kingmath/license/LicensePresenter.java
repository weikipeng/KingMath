package com.pengjunwei.kingmath.license;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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

    private static final String TAG = "LicensePresenter";
    protected LicenseInteractor.Interactor mInteractor;

    /**
     * 剪切板内容
     */
    protected String clipboardText;

    public LicensePresenter(Activity activity) {
        super(activity);
        setMVPView(new LicenseView(getProvider(), this));
        initData();
    }


    @Override
    protected void initData() {
        mInteractor = new LicenseInteractor.WebInteractor();

        getClipboardData();
    }

    protected void getClipboardData() {
        ClipboardManager clipboard = (ClipboardManager) provider.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // Examines the item on the clipboard. If getText() does not return null, the clip item contains the
        // text. Assumes that this application can only handle one item at a time.
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        // Gets the clipboard as text.
        clipboardText = item.getText().toString();

        if (!TextUtils.isEmpty(clipboardText)) {
            autoVerify(clipboardText);
        }

        if (mvpView instanceof ILicenseView) {
            ((ILicenseView) mvpView).updateClipboard(clipboardText);
        }
    }

    protected void autoVerify(final String license) {
        if (TextUtils.isEmpty(license)) {
            return;
        }

        RxPermissions rxPermissions = new RxPermissions(provider.getActivity()); // where this is an Activity instance

        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new RxSubscriber<Boolean>() {

            @Override
            public void onNext(Boolean granted) {
                if (granted) {
                    autoVerifyWithPermission(license);
                }
            }
        });
    }

    public static String getChannel(Context context) {
        String channel = "";
        try {
            ApplicationInfo ai     = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle          bundle = ai.metaData;
            channel = bundle.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }

        return channel;
    }

    protected void autoVerifyWithPermission(String license) {
        mInteractor.autoVerify(LicenseDao.getPhoneId(provider.getActivity()), license, getChannel(provider.getActivity()))
                .subscribe(new RxSubscriber<SLicenseVerifyResult>() {
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

        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new RxSubscriber<Boolean>() {

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

        if (result.errCode != 0) {
            provider.showToast(result.errMsg);
            return;
        }

        if (TextUtils.isEmpty(result.cellPhone)) {
            provider.showToast("激活软件失败");
            return;
        }

        String valueText = BaseInteractor.sGson.toJson(result);
        try {
            valueText = Base64.encodeToString(valueText.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(provider.getActivity());
        sharedPreferences.edit().putString(SunPowerPresenter.KEY_LICENSE, valueText).apply();

        Intent intent = new Intent(provider.getActivity(), MainActivity.class);
        provider.getActivity().startActivity(intent);
        provider.getActivity().finish();
    }

}
