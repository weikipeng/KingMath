package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.pengjunwei.kingmath.tool.FOpenLog;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BasePresenter implements IPresenter {
    protected IView            mvpView;
    protected IMVPProvider     provider;
    protected LifeCycleManager lifeCycleManager;

    public BasePresenter(Activity activity) {
        provider = new MVPProvider(activity);
    }

    public BasePresenter(View view) {
        provider = new MVPProvider(view);
    }

    protected void initData(){

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
        if (lifeCycleManager != null) {
            lifeCycleManager.callLifeCycleListeners();
        }
    }

    @Override
    public void onPause() {
        FOpenLog.e(getClass().getSimpleName() + "======> onPause");
        if (lifeCycleManager != null) {
            lifeCycleManager.callLifeCycleListeners();
        }
    }

    @Override
    public void onStop() {
        FOpenLog.e(getClass().getSimpleName() + "======> onStop");
        if (lifeCycleManager != null) {
            lifeCycleManager.callLifeCycleListeners();
        }
    }

    @Override
    public void finish() {
        FOpenLog.e(getClass().getSimpleName() + "======> finish");
        if (lifeCycleManager != null) {
            lifeCycleManager.callLifeCycleListeners();
        }
    }

    @Override
    public void onDestroy() {
        FOpenLog.e(getClass().getSimpleName() + "======> onDestroy");
        if (lifeCycleManager != null) {
            lifeCycleManager.callLifeCycleListeners();
            lifeCycleManager.clear();
        }
    }
}
