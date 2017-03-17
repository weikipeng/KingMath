package com.pengjunwei.kingmath.mvp;

/**
 * Created by WikiPeng on 2017/3/17 14:43.
 */
public interface ILifeCycleListener {

    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
