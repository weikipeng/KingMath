package com.pengjunwei.kingmath.mvp;

import android.app.Activity;
import android.view.View;

/**
 * Created by WikiPeng on 2017/3/11 15:38.
 */
public class MVPProvider implements IMVPProvider {
    private View view;

    public MVPProvider(Activity activity) {
        view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }
}
