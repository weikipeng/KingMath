package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

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
}
