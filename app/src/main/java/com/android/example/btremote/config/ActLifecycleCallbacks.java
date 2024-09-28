package com.android.example.btremote.config;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.android.example.btremote.hid.HidConstants;
import com.android.example.btremote.hid.HidUtils;

public class ActLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        ActivityTack.tack.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (HidConstants.HidDevice != null) {
            HidUtils.reConnect(activity);
            HidConstants.HidDevice = HidUtils.HidDevice;
            HidConstants.BtDevice = HidUtils.BtDevice;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityTack.tack.removeActivity(activity);
    }

}
