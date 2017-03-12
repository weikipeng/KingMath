package com.pengjunwei.kingmath.mvp;

import android.content.Intent;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 15:29.
 */
public interface IPresenter {
    IMVPProvider getProvider();

    void refresh(boolean isForce);

    void setIntent(Intent intent);

    <T extends IView> void setMVPView(T view);
}
