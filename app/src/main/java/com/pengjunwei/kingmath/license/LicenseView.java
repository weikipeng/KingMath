package com.pengjunwei.kingmath.license;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.IView;
import com.pengjunwei.kingmath.tool.RxSubscriber;

import java.util.concurrent.TimeUnit;

/**
 * Created by WikiPeng on 2017/3/20 14:05.
 */
public class LicenseView extends BaseMVPView implements View.OnClickListener, ILicenseView {

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
        RxView.clicks(btnAction).throttleFirst(300, TimeUnit.MILLISECONDS).
                subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        onClick(btnAction);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
                String text = licenseEditText.getText().toString();
                ((ILicensePresenter) presenter).onBtnActionClick(text);
                break;
        }
    }
}

