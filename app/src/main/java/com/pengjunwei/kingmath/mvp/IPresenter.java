package com.pengjunwei.kingmath.mvp;

import android.content.Intent;

/**
 * Created by WikiPeng on 2017/3/11 15:29.
 */
public interface IPresenter {
    void refresh(boolean isForce);

    void setIntent(Intent intent);
}
