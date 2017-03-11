package com.pengjunwei.kingmath.mvp.recyclerview;

import android.app.Activity;
import android.content.Intent;

import com.pengjunwei.kingmath.mvp.BasePresenter;

/**
 * Created by WikiPeng on 2017/3/11 15:34.
 */
public class BaseRecyclerPresenter extends BasePresenter implements IRecyclerPresenter {
    protected BaseRecyclerAdapter mAdapter;

    public BaseRecyclerPresenter(Activity activity) {
        super(activity);
    }
}
