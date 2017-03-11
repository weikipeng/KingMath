package com.pengjunwei.kingmath.sunpower;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerMVPView;

/**
 * Created by WikiPeng on 2017/3/11 15:42.
 */
public class SunPowerView extends BaseRecyclerMVPView implements View.OnClickListener {
    protected EditText mNumber;
    protected Button   mButton;

    public SunPowerView(IMVPProvider provider) {
        super(provider);
    }

    public <T extends IPresenter> SunPowerView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
    }

    protected void initView() {
        super.initView();
        mNumber = provider.findViewById(R.id.number);
        mButton = provider.findViewById(R.id.doAction);
    }

    protected void addEvent() {
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
                calculateSunPower();
                break;
        }
    }

    protected void calculateSunPower() {
        String valueText = mNumber.getText().toString();
        if (TextUtils.isEmpty(valueText)) {
            provider.showToast(R.string.tips_info_empty);
            return;
        }
        if (!TextUtils.isDigitsOnly(valueText)) {
            provider.showToast(R.string.tips_info_not_number);
            return;
        }

        if (presenter instanceof ISunPowerPresenter) {
            ((ISunPowerPresenter) presenter).calculateSunPower(Integer.parseInt(valueText));
        }
    }
}
