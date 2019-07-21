package com.dkv.applocker.controller.service_and_state_pattern.State;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dkv.applocker.activity.UnlockActivity;
import com.dkv.applocker.controller.service_and_state_pattern.ServiceController;
import com.dkv.applocker.controller.service_and_state_pattern.TopActivityProcessService;
import com.dkv.applocker.model.LockedAppList;

import java.io.Serializable;

public class LockedState implements State, Serializable {

    ServiceController parent;

    public LockedState(ServiceController topActivityProcessService) {
        parent = topActivityProcessService;
    }

    @Override
    public void changeActivity(String forePackage, Context context) {
        LockedAppList list = new LockedAppList(context);
        if(list.hasForePackageLocked(forePackage)) {
            askUserToEnterPassword(forePackage, context);
        }
        parent.setCurrentPackage(forePackage);
    }

    private void askUserToEnterPassword(String forePackage, Context context) {
        Intent intent = new Intent(context, UnlockActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Log.i("ParentState",parent.getCurrentState().toString());
    }

    @Override
    public String toString() {
        return "Locked";
    }
}
