package com.pengjunwei.kingmath.sunpower;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.pengjunwei.kingmath.MainActivity;
import com.pengjunwei.kingmath.SunPowerProfitsActivity;
import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.license.LicenseActivity;
import com.pengjunwei.kingmath.license.LicenseDao;
import com.pengjunwei.kingmath.license.LicenseInteractor;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.activity.IActivityPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.IRecyclerView;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.RxSubscriber;
import com.pengjunwei.kingmath.viewholder.ViewHolderFactor;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

/**
 * Created by WikiPeng on 2017/3/11 15:44.
 */
public class SunPowerPresenter extends BaseRecyclerPresenter implements ISunPowerPresenter, IActivityPresenter {
    public static final short REQUEST_CODE_CALCULATE_SUN_POWER = 95;
    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------
    protected SunPowerDao       mSunPowerDao;
    protected SunPower          mSunPower;
    protected SharedPreferences mSharedPreferences;
    public static final String KEY_LICENSE       = "license";
    public static final String KEY_LICENSE_VALUE = "licenseValue";
    protected LicenseInteractor.Interactor mLicenseInteractor;

    public SunPowerPresenter(Activity activity) {
        super(activity);
        mvpView = new SunPowerView(provider, this);

        initData();
    }

    protected void initData() {
        mAdapter = new SunPowerAdapter(this);
        mAdapter.getTypeProvider().register(FactorInfo.class, ViewHolderFactor.class
                , new ViewHolderFactor.LayoutProvider());
        ((IRecyclerView) mvpView).setAdapter(mAdapter);
        mSunPowerDao = new SunPowerDao(provider.getActivity());
        mSunPower = mSunPowerDao.getSunPower();

        //---
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(provider.getActivity());
        mLicenseInteractor = new LicenseInteractor.WebInteractor();
        checkLicense();
    }

    @Override
    public void refresh(boolean isForce) {
        super.refresh(isForce);
        mAdapter.clear();
        mAdapter.add(mSunPower.getFactorInfoList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void calculateSunPower(int number) {
        mSunPower.setNumber(number);
        Intent intent = new Intent(provider.getActivity(), SunPowerProfitsActivity.class);
        intent.putExtra(MainActivity.EXTRA_DATA, mSunPower);
        provider.getActivity().startActivityForResult(intent, REQUEST_CODE_CALCULATE_SUN_POWER);
    }

    @Override
    public void updateFactor(FactorInfo data) {
        if (data == null) {
            return;
        }

        switch (data.fieldName) {
            case "selfUsePercent":
                try {
                    data = data.clone();
                    data.value = Float.parseFloat(String.valueOf(data.value)) / 100f;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
        }

        changeFieldValue(mSunPower, SunPower.class, data.fieldName, data.value);
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    protected void changeFieldValue(Object object, Class clazz, String name, Object value) {
        Field declaredField = null;
        try {
            declaredField = clazz.getDeclaredField(name);
            boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);

            Class classType = declaredField.getType();

            String valueText = String.valueOf(value);

            if (!TextUtils.isEmpty(valueText)) {
                if (classType == int.class) {
                    value = Integer.parseInt(valueText);
                } else if (classType == float.class) {
                    value = Float.parseFloat(valueText);
                }
            }

//            if (!declaredField.getType().isInstance(value))
//                throw new IllegalArgumentException();

            declaredField.set(object, value);

            declaredField.setAccessible(accessible);
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalArgumentException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mSunPowerDao.save(mSunPower);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FOpenLog.e("kingMathFocus debug onActivityResult ====>");
        if (requestCode == REQUEST_CODE_CALCULATE_SUN_POWER &&
                resultCode == Activity.RESULT_OK && data != null) {
            mSunPower = data.getParcelableExtra(MainActivity.EXTRA_DATA);
            refresh(true);
        }
    }

    protected void checkLicense() {
        String license = mSharedPreferences.getString(KEY_LICENSE, "");
        if (TextUtils.isEmpty(license)) {
            startLicense();
            return;
        }

        RxPermissions rxPermissions = new RxPermissions(provider.getActivity()); // where this is an Activity instance

        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new RxSubscriber<Boolean>() {

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            verifyLicense();
                        }else{
                            MobclickAgent.onKillProcess(provider.getActivity());
                            System.exit(0);
                        }
                    }
                });
    }

    protected void verifyLicense(){
        try {
            String license = mSharedPreferences.getString(KEY_LICENSE, "");
            String               licenseText  = new String(Base64.decode(license, Base64.NO_WRAP), "UTF-8");
            SLicenseVerifyResult verifyResult = BaseInteractor.sGson.fromJson(licenseText, SLicenseVerifyResult.class);
            String               phoneId      = LicenseDao.getPhoneId(provider.getActivity());
            if (!TextUtils.isEmpty(phoneId) && phoneId.equals(verifyResult.cellPhone)) {
            } else {
                startLicense();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            startLicense();
            return;
        }
    }

    protected void startLicense() {
        Intent intent = new Intent(provider.getActivity(), LicenseActivity.class);
        provider.getActivity().startActivity(intent);
        provider.getActivity().finish();
    }
}
