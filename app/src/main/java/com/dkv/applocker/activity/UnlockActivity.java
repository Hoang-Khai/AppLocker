package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dkv.applocker.R;
import com.dkv.applocker.model.Password;

public class UnlockActivity extends AppCompatActivity {

    private EditText txtPassword;
    private Button btnOK;
    private Button btnCancel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        txtPassword = findViewById(R.id.txtPasswordRe);
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        textView = findViewById(R.id.textView);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressOkButton();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressCancelButton();
            }
        });
    }


    private void pressOkButton() {

        //Compare password in database and user's password
        String pass = txtPassword.getText().toString();
        Password p = new Password(pass,getApplicationContext());
        if (p.isMatchedWithTokenInDB()) {
//            finish intent
            finish();
        }
        else {
//            txtPassword.setText("");
//            textView.setText("Wrong Password");
            pressCancelButton();
        }
    }


    private void pressCancelButton() {
        Intent homeScreen = new Intent(Intent.ACTION_MAIN);
        homeScreen.addCategory(Intent.CATEGORY_HOME);
        homeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeScreen);
    }
}
