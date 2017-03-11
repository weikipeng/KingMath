package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

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
}
