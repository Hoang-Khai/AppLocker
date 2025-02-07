package com.dkv.applocker.controller.service_and_state_pattern.State;

import android.content.Context;
import android.util.Log;

import com.dkv.applocker.controller.service_and_state_pattern.ServiceController;
import com.dkv.applocker.controller.service_and_state_pattern.TopActivityProcessService;

import java.io.Serializable;

public class UnlockedState implements State, Serializable {

    ServiceController parent;

    public UnlockedState(ServiceController topActivityProcessService) {
        parent = topActivityProcessService;
    }

    @Override
    public void changeActivity(String forePackage, Context context) {
        String currentPackage = parent.getCurrentPackage();
        Log.i("CurrentAndFore",currentPackage + " / " + forePackage);
        if(!forePackage.equals(currentPackage)) {
            parent.setCurrentState(parent.getLockedState());
        }
        Log.i("ParentState",parent.getCurrentState().toString());
        parent.setCurrentPackage(forePackage);
    }

    @Override
    public String toString() {
        return "Unlocked";
    }
}
