package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dkv.applocker.R;

public class SettingActivity extends AppCompatActivity {
    private ImageView btnChangeP,btnInfo,btnBack;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnChangeP = findViewById(R.id.btnChangeP);
        btnInfo = findViewById(R.id.btnInfo);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(50,intent);
                finish();
            }
        });
        btnChangeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingActivity.this,ChangePasswordActivity.class);
                startActivityForResult(intent,200);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i("SettingOnDestroy","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
        super.onDestroy();
    }

//    @Override
//    protected void onStop() {
//        Log.i("SettingOnStop","is called");
//        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
//        sendBroadcast(intent);
//        super.onStop();
//    }
}
