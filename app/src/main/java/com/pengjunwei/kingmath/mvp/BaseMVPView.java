package com.pengjunwei.kingmath.mvp;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BaseMVPView implements IView {

    protected IMVPProvider provider;

    public BaseMVPView(IMVPProvider provider) {
        this.provider = provider;
    }

//    @Override
//    public <VT extends View> VT findViewById(@IdRes int id) {
//        return supporter.findViewById(id);
//    }
//
//    @Override
//    public <VT extends View> VT findViewByIdInAll(@IdRes int id) {
//        return supporter.findViewByIdInAll(id);
//    }

    IMVPProvider getMVPProvider() {
        return provider;
    }
}
