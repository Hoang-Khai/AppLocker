package com.dkv.applocker.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dkv.applocker.R;
import com.dkv.applocker.helper_for_sql.SQLHelper;
import com.dkv.applocker.model.Password;

import java.util.ArrayList;

public class SetPasswordActivity extends AppCompatActivity {

    //This class allow user to set a password to lock app if there is not any
    //If there is a password in SQLite, this activity will be used to change the password
    //1 EditText (Hidden by default) to enter old password
    //2 EditText to enter new Password
    //1 OK button to send old password and new password to PasswordController

    private ImageView btnChangeP, btnInfo, btnBack;
    private TextView txtChangePass, textViewSet, textView1R, textView2R, textViewCheck;
    private EditText txtPassword, txtPasswordRe;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        setRef();

        final Intent intent = new Intent();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(50, intent);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnChangeP.setClickable(false);
        btnChangeP.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPassword();
            }
        });

    }

    private void setRef() {
        textViewCheck = findViewById(R.id.textViewCheck);
        txtChangePass = findViewById(R.id.txtChangePass);
        textViewSet = findViewById(R.id.textViewSet);
        textView1R = findViewById(R.id.textView1R);
        textView2R = findViewById(R.id.textView2R);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordRe = findViewById(R.id.txtPasswordRe);

        btnSave = findViewById(R.id.btnSavePassword);
        btnChangeP = findViewById(R.id.btnChangeP);
        btnInfo = findViewById(R.id.btnInfo);
        btnBack = findViewById(R.id.btn_back);
        textViewCheck.setText("");
    }

    //Set the password with these two
    public void insertPassword() {
        //check if password and re-Password match
        if (txtPassword.getText().toString().equals(txtPasswordRe.getText().toString())) {
            Password password = new Password(txtPassword.getText().toString(), getApplicationContext());
            password.changePasswordInSQlite();

            Toast.makeText(getApplicationContext(), "Set password success", Toast.LENGTH_LONG).show();
            finish();
        } else {
            //if not, alert
            textViewCheck.setText("Two password doesn't match");
            textViewCheck.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("SetPassOnDestroy","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
        super.onDestroy();
    }

//    @Override
//    protected void onStop() {
//        Log.i("SetPassOnStop","is called");
//        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
//        sendBroadcast(intent);
//        super.onStop();
//    }
}