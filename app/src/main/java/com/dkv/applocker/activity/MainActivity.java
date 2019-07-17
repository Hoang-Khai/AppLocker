package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dkv.applocker.R;

public class MainActivity extends AppCompatActivity {
    private ImageView btnSetting;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
        //https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start

        btnSetting = findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,SetPasswordActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }
}
