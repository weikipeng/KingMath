package com.pengjunwei.kingmath.mvp;

import android.content.Intent;

/**
 * Created by WikiPeng on 2017/3/11 15:29.
 */
public interface IPresenter extends ILifeCycleListener {
    IMVPProvider getProvider();

    void refresh(boolean isForce);

    void setIntent(Intent intent);

    <T extends IView> void setMVPView(T view);

    <V extends IView> V getMVPView();
}
