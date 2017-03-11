package com.pengjunwei.kingmath.mvp;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BasePresenter implements IPresenter {
    protected IView        mvpView;
    protected IMVPProvider provider;

    @Override
    public void refresh(boolean isForce) {

    }
}
