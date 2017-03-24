package com.pengjunwei.kingmath.license;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.tool.RxSubscriber;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

/**
 * Created by WikiPeng on 2017/3/20 14:05.
 */
public class LicenseView extends BaseMVPView implements View.OnClickListener, ILicenseView {

    protected View loginLayout;

    protected EditText userName;
    protected EditText password;
    protected TextView btnLogin;

    protected EditText licenseEditText;
    protected TextView btnAction;


    //注册码列表
    protected TextView licenseListTextView;


    public <T extends IPresenter> LicenseView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
        initView();
        addEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        loginLayout = getMVPProvider().findViewById(R.id.loginLayout);
        userName = getMVPProvider().findViewById(R.id.userName);
        password = getMVPProvider().findViewById(R.id.password);
        btnLogin = getMVPProvider().findViewById(R.id.login);

        licenseEditText = getMVPProvider().findViewById(R.id.license);
        btnAction = getMVPProvider().findViewById(R.id.doAction);
        licenseListTextView = getMVPProvider().findViewById(R.id.licenseList);
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
        RxView.clicks(btnLogin).throttleFirst(300, TimeUnit.MILLISECONDS).
                subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        onClick(btnLogin);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doAction:
                String text = licenseEditText.getText().toString();
                if ("math".equalsIgnoreCase(text)) {
                    if (((ILicensePresenter) presenter).isLogin()) {
                        ((ILicensePresenter) presenter).showLicenseList();
                    } else {
                        loginLayout.setVisibility(View.VISIBLE);
                    }
                } else if (presenter instanceof ILicensePresenter) {
                    ((ILicensePresenter) presenter).verify(text);
                }
                break;
            case R.id.login:
                String userNameText = userName.getText().toString();
                String passwordText = password.getText().toString();
                if (presenter instanceof ILicensePresenter) {
                    ((ILicensePresenter) presenter).login(userNameText, passwordText);
                }
                break;
        }
    }

    @Override
    public void showLicenseList(boolean isShow, String showText) {
        if (isShow) {
            loginLayout.setVisibility(View.GONE);
            licenseListTextView.setVisibility(View.VISIBLE);
            licenseListTextView.setText(showText);
        }
    }
}

