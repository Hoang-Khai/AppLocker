package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dkv.applocker.R;

public class SetPasswordActivity extends AppCompatActivity {

    //This class allow user to set a password to lock app if there is not any
    //If there is a password in SQLite, this activity will be used to change the password
    //1 EditText (Hidden by default) to enter old password
    //2 EditText to enter new Password
    //1 OK button to send old password and new password to PasswordController

    private ImageView btnChangeP,btnInfo,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        btnChangeP = findViewById(R.id.btnChangeP);
        btnInfo = findViewById(R.id.btnInfo);
        btnBack = findViewById(R.id.btn_back);
        final Intent intent = new Intent();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(50,intent);
                finish();
            }
        });
    }
}
