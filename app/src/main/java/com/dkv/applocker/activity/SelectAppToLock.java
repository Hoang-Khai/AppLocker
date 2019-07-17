package com.dkv.applocker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dkv.applocker.R;

public class SelectAppToLock extends AppCompatActivity {

    //This class will provide a view to let user pick up which app will be locked
    //A list with checkbox to select application
    //A OK button to send the list application to LockedAppController
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_app_to_lock);

    }
}
