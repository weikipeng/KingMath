package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pengjunwei.kingmath.sunpower.SunPowerPresenter;

/**
 * Created by WikiPeng on 2017/3/10 14:58.
 */
public class MainActivity extends BaseMVPActivity {
    public static final String EXTRA_DATA = "data";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new SunPowerPresenter(this);

        mPresenter.refresh(true);

//        mMvpView = new SunPowerView();
//        initData();
//        addEvent();
    }


//    protected void initData() {
//        mSunPower = new SunPower();
//
//        //--
//
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(new LinearLayout
//                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setTextColor(getResources().getColor(R.color.colorText));
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        List<FactorInfo> factorInfoList = mSunPower.getFactorInfoList();
//
//        if (factorInfoList != null && factorInfoList.size() > 0) {
//            for (FactorInfo info : factorInfoList) {
//                stringBuilder.append(info.name)
//                        .append(info.value).append(info.unitText).append("\n");
//            }
//        }
//
//        textView.setText(stringBuilder.toString());
//        mTabLayout.addView(textView);
//    }

//    protected void addEvent() {
//        mButton.setOnClickListener(this);
////        mAlphaValue.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                if (!TextUtils.isEmpty(s) && TextUtils.isDigitsOnly(s)) {
////                    calculateAlpha();
////                } else {
////                    mResult.setText("");
////                }
////            }
////        });
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.doAction:
////                calculateAlpha();
//                break;
//        }
//    }


}
