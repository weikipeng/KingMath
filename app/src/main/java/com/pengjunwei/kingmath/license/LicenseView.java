package com.pengjunwei.kingmath.license;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;

/**
 * Created by WikiPeng on 2017/3/20 14:05.
 */
public class LicenseView extends BaseMVPView implements View.OnClickListener {

    protected EditText licenseEditText;
    protected TextView btnAction;

    public <T extends IPresenter> LicenseView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
        initView();
        addEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        licenseEditText = getMVPProvider().findViewById(R.id.license);
        btnAction = getMVPProvider().findViewById(R.id.doAction);
    }

    @Override
    protected void addEvent() {
        super.addEvent();
        btnAction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
                String text = licenseEditText.getText().toString();
                if (presenter instanceof ILicensePresenter) {
                    ((ILicensePresenter) presenter).verify(text);
                }
                break;
        }
    }
}

