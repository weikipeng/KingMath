package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

/**
 * Created by WikiPeng on 2017/3/11 15:38.
 */
public class MVPProvider implements IMVPProvider {
    protected View     view;
    protected Activity mActivity;

    public MVPProvider(Activity activity) {
        view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        mActivity = activity;
    }


    @Override
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     */
    @SuppressWarnings("unchecked")
    @Override
    public <VT extends View> VT findViewById(@IdRes int id) {
        if (view != null) {
            return (VT) view.findViewById(id);
        }
        return (VT) mActivity.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <VT extends View> VT findViewByIdInAll(@IdRes int id) {
        return (VT) mActivity.findViewById(id);
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    @Override
    public void showToast(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     *
     * @param textResId
     */
    @Override
    public void showToast(@StringRes int textResId) {
        Toast.makeText(mActivity, textResId, Toast.LENGTH_SHORT).show();
    }
}
