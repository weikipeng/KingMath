package com.pengjunwei.kingmath.tool;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 封装Subscriber
 * Created by WikiPeng on 2016/12/16 14:19.
 */
public abstract class RxSubscriber<T> implements Observer<T> {
    public static final int FLAG_NOT_ON_ERROR_COMPLETED = 1;
    protected int flag;

    public RxSubscriber() {

    }

    public RxSubscriber(int flag) {
        this.flag = flag;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        int tFlag = flag & FLAG_NOT_ON_ERROR_COMPLETED;
        if (tFlag != 1) {
            onComplete();
        }
    }
}