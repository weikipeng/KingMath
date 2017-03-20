package com.pengjunwei.kingmath.mvp;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BaseMVPView implements IView {
    protected IPresenter   presenter;
    protected IMVPProvider provider;

    public BaseMVPView(IMVPProvider provider) {
        this.provider = provider;
    }

    public <T extends IPresenter> BaseMVPView(IMVPProvider provider, T presenter) {
        this.provider = provider;
        this.presenter = presenter;
    }

    protected IMVPProvider getMVPProvider() {
        return provider;
    }

    protected void initView(){

    }

    protected void addEvent(){

    }

    @Override
    public <T extends IPresenter> void setPresenter(T presenter) {
        this.presenter = presenter;
    }
}
