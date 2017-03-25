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
import com.pengjunwei.kingmath.user.ILoginView;

import java.util.concurrent.TimeUnit;

/**
 * Created by WikiPeng on 2017/3/20 14:05.
 */
public class LicenseView extends BaseMVPView implements View.OnClickListener, ILicenseView {

    protected EditText licenseEditText;
    protected TextView btnAction;


    //注册码列表
    protected TextView licenseListTextView;

    //---
    protected ILoginView mLoginView;


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
        licenseListTextView = getMVPProvider().findViewById(R.id.licenseList);

        licenseCreateViewStub = getMVPProvider().findViewById(R.id.licenseCreateViewStub);
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

    @Override
    public void showLicenseList(boolean isShow, String showText) {
        if (isShow) {
            if (mLoginView instanceof IView) {
                ((IView) mLoginView).show(false);
            }

            initCreateLicenseView();
            licenseCreateLayout.setVisibility(View.VISIBLE);

            licenseListTextView.setVisibility(View.VISIBLE);
            licenseListTextView.setText(showText);
        } else {
            if (licenseCreateLayout != null) {
                licenseCreateLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setLoginView(IView loginView) {
        if (loginView instanceof ILoginView) {
            this.mLoginView = (ILoginView) loginView;
        }
    }


    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------
    protected ViewStub licenseCreateViewStub;
    protected View     licenseCreateLayout;
    protected EditText corporation;
    protected EditText createNum;
    protected TextView btnCreate;

    protected void initCreateLicenseView() {
        if (licenseCreateLayout == null) {
            licenseCreateLayout = licenseCreateViewStub.inflate();
            corporation = (EditText) licenseCreateLayout.findViewById(R.id.corporationName);
            createNum = (EditText) licenseCreateLayout.findViewById(R.id.createNumber);
            btnCreate = (TextView) licenseCreateLayout.findViewById(R.id.createLicense);

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (presenter instanceof ILicensePresenter) {
                        String corporationText = corporation.getText().toString();
                        String numText         = createNum.getText().toString();
                        ((ILicensePresenter) presenter).createLicense(corporationText, numText);
                    }
                }
            });
        }
    }
}

