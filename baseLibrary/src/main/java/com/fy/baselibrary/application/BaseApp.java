package com.fy.baselibrary.application;


import android.app.Application;

/**
 * 基础 application
 * Created by fangs on 2017/5/5.
 */
public class BaseApp extends Application {

    private static BaseApp mApplication; // 单例模式

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

//        设置activity 生命周期回调
        registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks());
    }

    /**
     * 单例模式，获取 BaseApplication的实例
     *
     * @return
     */
    public static BaseApp getAppCtx() {
        return mApplication;
    }
}
