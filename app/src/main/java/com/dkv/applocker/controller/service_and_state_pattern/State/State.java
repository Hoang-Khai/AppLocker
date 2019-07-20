package com.dkv.applocker.controller.service_and_state_pattern.State;

import android.content.Context;

public interface State {
    public void changeActivity(String forePackage, Context context);
}
