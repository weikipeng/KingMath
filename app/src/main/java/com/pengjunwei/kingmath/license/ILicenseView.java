package com.pengjunwei.kingmath.license;

import com.pengjunwei.kingmath.mvp.IView;
import com.pengjunwei.kingmath.user.ILoginView;

/**
 * Created by WikiPeng on 2017/3/20 14:06.
 */
public interface ILicenseView {
    void showLicenseList(boolean isShow, String showText);
    void setLoginView(IView loginView);
}
