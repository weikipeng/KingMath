package com.pengjunwei.kingmath.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.pengjunwei.kingmath.mvp.BasePresenter;

/**
 * Created by WikiPeng on 2017/3/17 16:59.
 */
public class BaseActivityPresenter extends BasePresenter implements IActivityPresenter {

    public BaseActivityPresenter(Activity activity) {
        super(activity);
    }

    public BaseActivityPresenter(View view) {
        super(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
