package com.dkv.applocker.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TopActivityProcessService extends Service {
    public TopActivityProcessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
