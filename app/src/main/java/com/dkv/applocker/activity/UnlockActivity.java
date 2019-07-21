package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dkv.applocker.R;
import com.dkv.applocker.controller.service_and_state_pattern.ServiceController;
import com.dkv.applocker.controller.service_and_state_pattern.TopActivityProcessService;
import com.dkv.applocker.model.Password;

public class UnlockActivity extends AppCompatActivity {

    private EditText txtPassword;
    private Button btnOK;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        txtPassword = findViewById(R.id.txtPasswordRe);
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressOkButton();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToHomeScreen();
            }
        });
    }


    private void pressOkButton() {

        //Compare password in database and user's password
        String pass = txtPassword.getText().toString();
        Password p = new Password(pass,getApplicationContext());
        if (p.isMatchedWithTokenInDB()) {
            //if match, close activity
            ServiceController locker = ServiceController.getOurInstance();
            locker.setCurrentState(locker.getUnlockedState());
            finish();
        }
        else {
            //wrong password
            getToHomeScreen();
        }
    }

    private void getToHomeScreen() {
        Intent homeScreen = new Intent(Intent.ACTION_MAIN);
        homeScreen.addCategory(Intent.CATEGORY_HOME);
        homeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeScreen);
    }

    @Override
    public void onBackPressed() {
        getToHomeScreen();
    }

    @Override
    protected void onDestroy() {
        Log.i("UnlockOnDestroy","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
        super.onDestroy();
    }

//    @Override
//    protected void onStop() {
//        Log.i("UnlockOnStop","is called");
//        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
//        sendBroadcast(intent);
//        super.onStop();
//    }
}
