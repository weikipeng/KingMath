package com.pengjunwei.kingmath.sunpower;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.pengjunwei.kingmath.MainActivity;
import com.pengjunwei.kingmath.SunPowerProfitsActivity;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.activity.IActivityPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.IRecyclerView;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.viewholder.ViewHolderFactor;

import java.lang.reflect.Field;

/**
 * Created by WikiPeng on 2017/3/11 15:44.
 */
public class SunPowerPresenter extends BaseRecyclerPresenter implements ISunPowerPresenter, IActivityPresenter {
    public static final short REQUEST_CODE_CALCULATE_SUN_POWER = 95;
    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------
    protected SunPowerDao mSunPowerDao;
    protected SunPower    mSunPower;

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
}
