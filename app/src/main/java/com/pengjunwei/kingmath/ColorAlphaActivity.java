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

/**
 * Created by WikiPeng on 2017/3/10 17:14.
 */
public class ColorAlphaActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText mAlphaValue;
    protected Button   mButton;
    protected TextView mResult;

    protected LinearLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_alpha);

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
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 100; i >= 0; i -= 5) {
            stringBuilder.append(i);
            stringBuilder.append("%:");
            stringBuilder.append(getAlphaText(i));
            stringBuilder.append("\n");
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
        mResult.setText(getAlphaText(num));
    }

    public String getAlphaText(int percent) {
        if (percent > 100 || percent < 0) {
            Toast.makeText(this, "请输入正确的范围", Toast.LENGTH_SHORT).show();
            return "";
        }
//        int    result     = 255 * percent / 100;
//        String resultText = Integer.toHexString(result).toUpperCase();
//        if (resultText.length() == 1) {
//            resultText = "0" + resultText;

        int    result     = Math.round(255 * percent / 100f);
        String resultText = Integer.toHexString(result).toUpperCase();
        if (resultText.length() == 1) {
            resultText = "0" + resultText;
        }
        return resultText;
    }
}
