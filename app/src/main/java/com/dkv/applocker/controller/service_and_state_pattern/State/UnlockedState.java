package com.dkv.applocker.controller.service_and_state_pattern.State;

import android.content.Context;

import com.dkv.applocker.controller.service_and_state_pattern.TopActivityProcessService;

public class UnlockedState implements State {

    TopActivityProcessService parent;

    public UnlockedState(TopActivityProcessService topActivityProcessService) {
        parent = topActivityProcessService;
    }

    @Override
    public void changeActivity(String forePackage, Context context) {
        String currentPackage = parent.getCurrentPackage();
        if(!forePackage.equals(currentPackage)) {
            parent.setCurrentState(parent.getLockedState());
        }
    }
}
