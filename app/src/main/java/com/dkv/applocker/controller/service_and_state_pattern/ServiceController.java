package com.dkv.applocker.controller.service_and_state_pattern;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.dkv.applocker.controller.service_and_state_pattern.State.LockedState;
import com.dkv.applocker.controller.service_and_state_pattern.State.State;
import com.dkv.applocker.controller.service_and_state_pattern.State.UnlockedState;

public class ServiceController {
    private static final ServiceController ourInstance = new ServiceController();
    private ActivityInfo activityInfo;
    private ComponentName componentName;
    private State lockedState;
    private State unlockedState;
    private State currentState;
    private String currentPackage;
    private Context context;

    static ServiceController getInstance() {
        return ourInstance;
    }

    private ServiceController() {
        lockedState = new LockedState(this);
        unlockedState = new UnlockedState(this);
        currentState = lockedState;
        currentPackage = "com.dkv.applocker";
    }

    public void process() {
        boolean isActivity = activityInfo != null;
        if (isActivity) {
            String forePackage = componentName.getPackageName();
            currentState.changeActivity(forePackage, context);
        }
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

    public void setCurrentState(State newState) {
        this.currentState = newState;
//        Log.i("SetOrNot","Set to " + newState.toString() + " / " + this.currentState.toString());
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentPackage(String newPackage) {
        this.currentPackage = newPackage;
    }

    public static ServiceController getOurInstance() {
        return ourInstance;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public void setComponentName(ComponentName componentName) {
        this.componentName = componentName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
