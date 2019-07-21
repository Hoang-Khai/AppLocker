package com.dkv.applocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dkv.applocker.R;
import com.dkv.applocker.exception.WrongPasswordException;
import com.dkv.applocker.model.Password;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView btnBack;
    private EditText oldPass,newPass,reEnNewPass;
    private Button btnSave,btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setRef();

        //Quay trở lại trang SettingActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(200);
                finish();
            }
        });

        //Reset lại edit text
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTXT();
            }
        });

        //Chức năng nút Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPass();
            }
        });

    }

    //Reset all EditText
    private void resetTXT() {
        oldPass.setText("");
        newPass.setText("");
        reEnNewPass.setText("");
    }

    //Change the password to the new one
    private void setPass() {
        String oldPassword = oldPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String rePassword = reEnNewPass.getText().toString();
        //Check if password and re-Password match
        if(newPassword.equals(rePassword)) {
            Password password = new Password(newPassword,getApplicationContext());
            try {
                //try to change password
                password.changePasswordInSQlite(oldPassword);
                resetTXT();
                notifyUser("Success");
            } catch (WrongPasswordException e) {
                //Exception is throw when the oldPassword is not match the one in DB
                resetTXT();
                notifyUser("Old password was not correct.");
            }
        } else {
            //if not, notify user
            resetTXT();
            notifyUser("Password and re-enter password didn't match.");
        }
    }

    private void notifyUser(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    //get references from activity
    private void setRef() {
        btnBack = findViewById(R.id.btnBackToSetting);
        oldPass = findViewById(R.id.txtOldPassword);
        newPass = findViewById(R.id.txtNewPassword);
        reEnNewPass = findViewById(R.id.txtReEnterNewPassword);
        btnSave = findViewById(R.id.btnSave);
        btnReset = findViewById(R.id.btnReset);
    }

    @Override
    protected void onDestroy() {
        Log.i("ChangePassOnDestroy","is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
        super.onDestroy();
    }

//    @Override
//    protected void onStop() {
//        Log.i("ChangePassOnStop","is called");
//        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
//        sendBroadcast(intent);
//        super.onStop();
//    }
//
//
}
