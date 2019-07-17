package com.dkv.applocker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dkv.applocker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
        //https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start
        //https://github.com/savvisingh/AppLock
        //https://github.com/SubhamTyagi/AppLock
        //https://github.com/mattsilber/applock
    }
}
