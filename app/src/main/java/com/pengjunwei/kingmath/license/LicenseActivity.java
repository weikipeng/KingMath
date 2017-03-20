package com.pengjunwei.kingmath.license;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pengjunwei.kingmath.BaseMVPActivity;
import com.pengjunwei.kingmath.R;

/**
 * Created by WikiPeng on 2017/3/20 13:55.
 */
public class LicenseActivity extends BaseMVPActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        mPresenter = new LicensePresenter(this);
    }
}
