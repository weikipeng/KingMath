package com.pengjunwei.kingmath;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.facebook.stetho.Stetho;
import com.pengjunwei.kingmath.base.BaseInteractor;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by WikiPeng on 2017/3/12 11:11.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        Stetho.initializeWithDefaults(this);
        BaseInteractor.init(this);
        //友盟 场景类型设置接口
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
