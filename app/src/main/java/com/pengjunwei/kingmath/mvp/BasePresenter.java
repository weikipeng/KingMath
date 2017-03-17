package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.pengjunwei.kingmath.tool.FOpenLog;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BasePresenter implements IPresenter {
    protected IView        mvpView;
    protected IMVPProvider provider;

    public BasePresenter(Activity activity) {
        provider = new MVPProvider(activity);
    }

    public BasePresenter(View view) {
        provider = new MVPProvider(view);
    }

    @Override
    public IMVPProvider getProvider() {
        return provider;
    }

    @Override
    public void refresh(boolean isForce) {

    }

    @Override
    public void setIntent(Intent intent) {

    }

    @Override
    public <T extends IView> void setMVPView(T view) {
        mvpView = view;
    }

    @Override
    public void onResume() {
        FOpenLog.e(getClass().getSimpleName() + "======> onResume");
    }

    @Override
    public void onPause() {
        FOpenLog.e(getClass().getSimpleName() + "======> onPause");
    }

    @Override
    public void onStop() {
        FOpenLog.e(getClass().getSimpleName() + "======> onStop");
    }

    @Override
    public void onDestroy() {
        FOpenLog.e(getClass().getSimpleName() + "======> onDestroy");
    }
}
