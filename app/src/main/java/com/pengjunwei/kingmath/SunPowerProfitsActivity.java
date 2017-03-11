package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.ResultShowInfo;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 15:23.
 */
public class SunPowerProfitsActivity extends AppCompatActivity {
    //    protected TextView mResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
