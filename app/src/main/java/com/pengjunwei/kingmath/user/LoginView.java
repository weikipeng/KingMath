package com.pengjunwei.kingmath.user;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.license.ILicensePresenter;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.tool.RxSubscriber;

import java.util.concurrent.TimeUnit;

/**
 * Created by WikiPeng on 2017/3/24 13:40.
 */
public class LoginView extends BaseMVPView implements ILoginView, View.OnClickListener {
//    protected View loginLayout;

    protected EditText userName;
    protected EditText password;
    protected TextView btnLogin;

    protected boolean isInit;


    public LoginView(IMVPProvider provider) {
        this(provider, null);
    }

    public <T extends IPresenter> LoginView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
        if (provider.isViewStub()) {
            return;
        }

        init();
    }

    protected boolean init() {
        if (!isInit) {
            initView();
            initData();
            addEvent();
            isInit = true;
        }
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
//        loginLayout = getMVPProvider().findViewById(R.id.loginLayout);
        userName = getMVPProvider().findViewById(R.id.userName);
        password = getMVPProvider().findViewById(R.id.password);
        btnLogin = getMVPProvider().findViewById(R.id.login);
    }

    protected void initData() {

    }

    @Override
    protected void addEvent() {
        super.addEvent();
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
            case R.id.login:
                String userNameText = userName.getText().toString();
                String passwordText = password.getText().toString();
                if (presenter instanceof ILoginPresenter) {
                    ((ILoginPresenter) presenter).login(userNameText, passwordText);
                }
                break;
        }
    }

    @Override
    public void show(boolean isShow) {
        super.show(isShow);
        if (isShow) {
            init();
        }
    }
}
