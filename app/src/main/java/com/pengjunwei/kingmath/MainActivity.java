package com.pengjunwei.kingmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.ResultShowInfo;
import com.pengjunwei.kingmath.model.SunPower;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/10 14:58.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText mAlphaValue;
    protected Button   mButton;
    protected TextView mResult;

    protected LinearLayout mTabLayout;


    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------
    protected SunPower mSunPower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        addEvent();
    }


    protected void initView() {
        mAlphaValue = (EditText) findViewById(R.id.alphaValue);
        mButton = (Button) findViewById(R.id.doAction);
        mResult = (TextView) findViewById(R.id.resultText);
        mTabLayout = (LinearLayout) findViewById(R.id.tabLayout);
    }

    protected void initData() {
        mSunPower = new SunPower();

        //--

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        StringBuilder stringBuilder = new StringBuilder();

        List<FactorInfo> factorInfoList = mSunPower.getFactorInfoList();

        if (factorInfoList != null && factorInfoList.size() > 0) {
            for (FactorInfo info : factorInfoList) {
                stringBuilder.append(info.name)
                        .append(info.value).append(info.unitText).append("\n");
            }
        }

        textView.setText(stringBuilder.toString());
        mTabLayout.addView(textView);
    }

    protected void addEvent() {
        mButton.setOnClickListener(this);
        mAlphaValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && TextUtils.isDigitsOnly(s)) {
                    calculateAlpha();
                } else {
                    mResult.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
                calculateAlpha();
                break;
        }
    }

    protected void calculateAlpha() {
        String text = mAlphaValue.getText().toString();
        if (TextUtils.isEmpty(text) || !TextUtils.isDigitsOnly(text)) {
            Toast.makeText(this, "仅支持数字", Toast.LENGTH_SHORT).show();
            return;
        }

        int num = Integer.parseInt(text);
        mSunPower.setNumber(num);

        StringBuilder stringBuilder = new StringBuilder();

        List<ResultShowInfo> factorInfoList = mSunPower.getResultInfoList();

        if (factorInfoList != null && factorInfoList.size() > 0) {
            for (FactorInfo info : factorInfoList) {
                stringBuilder.append(info.name)
                        .append(info.value).append(info.unitText).append("\n");
            }
        }

        mResult.setText(stringBuilder.toString());
    }
}
