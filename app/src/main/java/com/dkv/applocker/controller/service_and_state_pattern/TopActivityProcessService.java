package com.dkv.applocker.controller.service_and_state_pattern;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import com.dkv.applocker.controller.service_and_state_pattern.State.LockedState;
import com.dkv.applocker.controller.service_and_state_pattern.State.State;
import com.dkv.applocker.controller.service_and_state_pattern.State.UnlockedState;

public class TopActivityProcessService extends AccessibilityService {

    State lockedState;
    State unlockedState;

    State currentState;
    String currentPackage;

    public TopActivityProcessService() {
        lockedState = new LockedState(this);
        unlockedState = new UnlockedState(this);

        currentState = lockedState;
        currentPackage = "com.dkv.applocker";
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
        String forePackage="";
        //Phan nay dang viet do
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = tryGetActivity(componentName);
                boolean isActivity = activityInfo != null;
                if (isActivity) {
                    forePackage = componentName.getPackageName();
                    currentState.changeActivity(forePackage, getApplicationContext());
                }
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

    public String getCurrentPackage() {
        return currentPackage;
    }

    public State getLockedState() {
        return lockedState;
    }

    public State getUnlockedState() {
        return unlockedState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

}
