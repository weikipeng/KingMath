package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by WikiPeng on 2017/3/11 15:38.
 */
public interface IMVPProvider {
    Activity getActivity();

    <VT extends View> VT findViewById(@IdRes int id);

    <VT extends View> VT findViewByIdInAll(@IdRes int id);

    void showToast(String text);

    void showToast(@StringRes int textResId);
}

