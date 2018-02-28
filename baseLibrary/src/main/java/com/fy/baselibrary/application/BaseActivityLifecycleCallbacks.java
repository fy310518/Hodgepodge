package com.fy.baselibrary.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.fy.baselibrary.utils.L;

import butterknife.ButterKnife;

/**
 * activity 生命周期回调 (api 14+)
 * 注意：使用本框架activity 与 activity 之间传递数据 统一使用 Bundle
 * Created by fangs on 2017/5/18.
 */
public class BaseActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = "ActivityCallbacks";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        L.d(TAG, "Create()");

        BaseActivityBean activityBean = new BaseActivityBean();
//        注册屏幕旋转监听
        BaseOrientoinListener orientoinListener = new BaseOrientoinListener(activity);
        boolean autoRotateOn = (android.provider.Settings.System.getInt(activity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);

        //检查系统是否开启自动旋转
        if (autoRotateOn) {
            orientoinListener.enable();
        }

        activityBean.setOrientoinListener(orientoinListener);

        activityBean.setUnbinder(ButterKnife.bind(activity));

        activity.getIntent().putExtra("ActivityBean", activityBean);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "Start()");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "Resume()");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        L.d(TAG, "Pause()");

    }

    @Override
    public void onActivityStopped(Activity activity) {
        L.d(TAG, "Stop()");

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        L.d(TAG, "SaveInstanceState()");

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        L.d(TAG, "Destroy()");

        BaseActivityBean activityBean = (BaseActivityBean) activity.getIntent().getSerializableExtra("ActivityBean");
        if (null != activityBean) {
            activityBean.getUnbinder().unbind();
            activityBean.getOrientoinListener().disable();//销毁 屏幕旋转监听
        }
    }
}
