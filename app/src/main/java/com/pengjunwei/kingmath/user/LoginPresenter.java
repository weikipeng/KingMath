package com.pengjunwei.kingmath.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.mvp.BasePresenter;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.pojo.SLoginResult;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.RxSubscriber;

import java.io.UnsupportedEncodingException;

/**
 * Created by WikiPeng on 2017/3/24 13:40.
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    protected UserInteractor.Interactor mInteractor;
    protected SharedPreferences         mSharedPreferences;
    protected LoginListener             mLoginListener;

    public LoginPresenter(Activity activity) {
        super(activity);
        initData();
    }

    public LoginPresenter(IMVPProvider provider) {
        super(provider);
        initData();
    }

    public LoginPresenter(View view) {
        super(view);
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        mInteractor = new UserInteractor.WebInteractor();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(provider.getActivity());
        mvpView = new LoginView(provider, this);
    }

    @Override
    public boolean isLogin() {
        return mSharedPreferences.contains(BaseInteractor.PARAM_AUTHORIZATION);
    }

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(provider.getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(provider.getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        mInteractor.login(userName, password).subscribe(new RxSubscriber<SLoginResult>() {

            @SuppressLint("CommitPrefEdits")
            @Override
            public void onNext(SLoginResult result) {
                handleLoginResult(result);
            }
        });
    }

    protected void handleLoginResult(SLoginResult result) {
        boolean isSuccess = false;
        if (result != null) {
            FOpenLog.e("result===>" + BaseInteractor.sGson.toJson(result));
            if (!TextUtils.isEmpty(result.res)) {
                mSharedPreferences.edit().putString(BaseInteractor.PARAM_AUTHORIZATION, result.res).apply();
                BaseInteractor.sAuthorization = result.res;
                String deResult = null;
                try {
                    deResult = new String(Base64.decode(result.res, Base64.NO_WRAP), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                FOpenLog.e("deResult===>" + deResult);
                isSuccess = true;
            }
        }

        if (!isSuccess) {
            Toast.makeText(provider.getActivity(), "登录失败，请重试", Toast.LENGTH_SHORT).show();
        }

        if (mLoginListener != null) {
            mLoginListener.onLogin(isSuccess);
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.mLoginListener = listener;
    }
}
