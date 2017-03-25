package com.pengjunwei.kingmath.user;

/**
 * Created by WikiPeng on 2017/3/24 13:40.
 */
public interface ILoginPresenter {
    boolean isLogin();

    void login(String userName, String password);

    void setLoginListener(LoginListener listener);
}
