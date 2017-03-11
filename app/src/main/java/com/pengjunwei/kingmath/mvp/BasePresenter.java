package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BasePresenter implements IPresenter {
    protected IView        mvpView;
    protected IMVPProvider provider;

    public BasePresenter(Activity activity) {
        provider = new MVPProvider(activity);
    }

    @Override
    public void refresh(boolean isForce) {

    }

    @Override
    public void setIntent(Intent intent) {

    }
}
