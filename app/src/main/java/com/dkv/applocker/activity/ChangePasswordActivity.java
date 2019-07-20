package com.dkv.applocker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dkv.applocker.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView btnBack;
    private EditText oldPass,newPass,reEnNewPass;
    private Button btnSave,btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnBack = findViewById(R.id.btnBackToSetting);
        oldPass = findViewById(R.id.txtOldPassword);
        newPass = findViewById(R.id.txtNewPassword);
        reEnNewPass = findViewById(R.id.txtReEnterNewPassword);
        btnSave = findViewById(R.id.btnSave);
        btnReset = findViewById(R.id.btnReset);

        //Quay trở lại trang MainAcitivity
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
                oldPass.setText("");
                newPass.setText("");
                reEnNewPass.setText("");
            }
        });

        //Chức năng nút Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check old password  giong pass trong db

                // check new password = re enter new password

            }
        });

    }
}
