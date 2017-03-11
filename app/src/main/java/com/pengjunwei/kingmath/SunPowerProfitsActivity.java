package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pengjunwei.kingmath.sunpower.profits.SunPowerProfitsPresenter;

/**
 * Created by WikiPeng on 2017/3/11 15:23.
 */
public class SunPowerProfitsActivity extends BaseMVPActivity {
    //    protected TextView mResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_power_profits);
        mPresenter = new SunPowerProfitsPresenter(this);
        mPresenter.setIntent(getIntent());
        mPresenter.refresh(true);
    }

//    protected void calculateAlpha() {
//        String text = mAlphaValue.getText().toString();
//        if (TextUtils.isEmpty(text) || !TextUtils.isDigitsOnly(text)) {
//            Toast.makeText(this, "仅支持数字", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        int num = Integer.parseInt(text);
//        mSunPower.setNumber(num);
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        List<ResultShowInfo> factorInfoList = mSunPower.getResultInfoList();
//
//        if (factorInfoList != null && factorInfoList.size() > 0) {
//            for (FactorInfo info : factorInfoList) {
//                stringBuilder.append(info.name)
//                        .append(info.value).append(info.unitText).append("\n");
//            }
//        }
//
//        mResult.setText(stringBuilder.toString());
//    }
}
