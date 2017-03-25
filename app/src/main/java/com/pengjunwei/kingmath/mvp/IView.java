package com.pengjunwei.kingmath.mvp;

/**
 * Created by WikiPeng on 2017/3/11 15:29.
 */
public interface IView {
    <T extends IPresenter> void setPresenter(T presenter);

    void show(boolean isShow);
}
