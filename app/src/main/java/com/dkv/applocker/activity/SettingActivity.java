package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
