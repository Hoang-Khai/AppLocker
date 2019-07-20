package com.dkv.applocker.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        //tạo + mở kết nối với db
        sqlHelper = new SQLHelper(this);

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
        final Intent intent = new Intent();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(50, intent);
                finish();
            }
        });

        if (checkPasswordInDatabase() == true) {
            btnSave.setClickable(false);
            btnSave.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            txtPasswordRe.setVisibility(View.GONE);
            textViewSet.setVisibility(View.GONE);
            textView1R.setVisibility(View.GONE);
            textView2R.setVisibility(View.GONE);
        } else {
            btnChangeP.setClickable(false);
            btnChangeP.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertPassword();
                }
            });
        }
    }

    public void insertPassword() {
//        boolean loopCheck= true;
//        while(loopCheck) {
        if (txtPassword.getText().toString().equals(txtPasswordRe.getText().toString())) {
//                loopCheck = false;
//            checkingData = 0;
            Password password = new Password();
            password.setPassword(txtPassword.getText().toString());
            Intent intent = new Intent();
            textViewCheck.setText("Set password success!");
            intent.putExtra("password", password);
            setResult(200, intent);
            finish();
        } else {
//                loopCheck=true;
            textViewCheck.setText("Two password doesn't match");
            textViewCheck.setTextColor(Color.RED);
//            checkingData = 1;
//            }
        }
    }

    private boolean checkPasswordInDatabase() {
        db = sqlHelper.getReadableDatabase();
        String password = "SELECT * FROM Password";
        Cursor cursor = db.rawQuery(password, null);

        if (cursor.moveToFirst()) {
            String pass = cursor.getString(cursor.getColumnIndex("Password"));
            if (pass.equals(null)) {
                return false;
            }
        }
        return true;
    }
}