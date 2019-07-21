package com.dkv.applocker.controller.service_and_state_pattern;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.dkv.applocker.controller.service_and_state_pattern.State.LockedState;
import com.dkv.applocker.controller.service_and_state_pattern.State.State;
import com.dkv.applocker.controller.service_and_state_pattern.State.UnlockedState;

import java.io.Serializable;
import java.security.Provider;

public class TopActivityProcessService extends AccessibilityService implements Serializable {


    public TopActivityProcessService() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //Khong can quan tam dau, phan config thoi
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 16)
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String forePackage = "";
        //Phan nay dang viet do
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null && !event.getPackageName().toString().equals("com.dkv.applocker") && !event.getPackageName().toString().contains("com.google.android.inputmethod")) {
                Log.i("ActivityChange", event.getPackageName().toString());
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );
                ActivityInfo activityInfo = tryGetActivity(componentName);
                ServiceController serviceController = ServiceController.getInstance();
                serviceController.setComponentName(componentName);
                serviceController.setActivityInfo(activityInfo);
                serviceController.setContext(getApplicationContext());

                serviceController.process();
            }
        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("TaskRemove","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("ServiceDestroyed","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }
}
