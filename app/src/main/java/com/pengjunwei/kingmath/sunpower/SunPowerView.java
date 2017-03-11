package com.pengjunwei.kingmath.sunpower;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;

/**
 * Created by WikiPeng on 2017/3/11 15:42.
 */
public class SunPowerView extends BaseMVPView implements View.OnClickListener {
    protected EditText mAlphaValue;
    protected Button   mButton;

    protected LinearLayout mTabLayout;

    public SunPowerView(IMVPProvider mvpProvider) {
        super(mvpProvider);
    }


    protected void initView() {
//        mAlphaValue = (EditText) findViewById(R.id.alphaValue);
//        mButton = (Button) findViewById(R.id.doAction);
////        mResult = (TextView) findViewById(R.id.resultText);
//        mTabLayout = (LinearLayout) findViewById(R.id.tabLayout);
    }

    protected void addEvent() {
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
//                calculateAlpha();
                break;
        }
    }
}
