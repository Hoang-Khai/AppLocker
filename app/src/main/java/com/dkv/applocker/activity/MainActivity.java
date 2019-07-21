package com.dkv.applocker.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.dkv.applocker.R;
import com.dkv.applocker.controller.AppListAdapter;
import com.dkv.applocker.controller.service_and_state_pattern.ServiceController;
import com.dkv.applocker.controller.service_and_state_pattern.TopActivityProcessService;
import com.dkv.applocker.model.AppDisplayer;
import com.dkv.applocker.model.LockedAppList;
import com.dkv.applocker.model.Password;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView btnSetting;
    private List<AppDisplayer> apps;
    private ListView appListView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Reference:
//        https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
//        https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start
//        https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
//        https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start
//        https://github.com/savvisingh/AppLock
//        https://github.com/SubhamTyagi/AppLock
//        https://github.com/mattsilber/applock

//        This part is for reset SQLite, use for debug only
//        Password p = new Password(getApplicationContext());
//        p.delPassword();
//        LockedAppList lockedAppList = new LockedAppList(getApplicationContext());
//        lockedAppList.delList();

//        Ask for permission
//        askPermission();

        //First time, if there are no passwords in DB, let user create one
        Password password = new Password(getApplicationContext());
        if (!password.hasPasswordInDB()) {
            intent = new Intent(MainActivity.this, SetPasswordActivity.class);
            startActivity(intent);
        } else {
            //If there is a password, ask user to enter it before using this app
            unlockDialog();
        }

        //Button setting is for change password only
        btnSetting = findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //Show list of application
        apps = new ArrayList<>();
        getAllApp(); //into the apps : ArrayList
        appListView = findViewById(R.id.appListView);
        appListView.setAdapter(new AppListAdapter(MainActivity.this, apps));
        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //When an app is select
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("ItemClick",parent.getItemAtPosition(position).getClass().toString());
                changeAppState(parent, view, position, id);
            }
        });
    }

//    private void askPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS},
//                        101);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//            // Permission has already been granted
//        }
//
//    }

    //Change 1 app from Lock to Unlock and vice versa
    private void changeAppState(AdapterView<?> parent, View view, int position, long id) {
        //First get the selected app and its package's name
        AppDisplayer selected = (AppDisplayer) parent.getItemAtPosition(position);
        String selectedPackage = selected.getPackageName();
//        Log.i("TouchPackage",selectedPackage);

        //Change the app's state in SQLite
        LockedAppList lockedAppList = new LockedAppList(getApplicationContext());
        if (lockedAppList.hasForePackageLocked(selectedPackage)) {
            lockedAppList.deletePackageFromDB(getApplicationContext(), selectedPackage);
        } else {
            lockedAppList.writePackageToDB(getApplicationContext(), selectedPackage);
        }

        //Update list view
        appListView.setAdapter(new AppListAdapter(MainActivity.this, apps));
    }

    //Get all application on device to apps : ArrayList
    public void getAllApp() {

        //Initiate tools
        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // ResolveInfo get all app that have launcher
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));


        //Browse each app is listed by ResolveInfo
        int appListNumber = appList.size();
        for (int i = 0; i < appListNumber; i++) {
            ResolveInfo p = appList.get(i);
            //Get name and icon from app
            AppDisplayer app = getAppDisplayFromResolveInfo(p);
            apps.add(app);
        }
    }

    //From the Resolve, get name and icon from app
    private AppDisplayer getAppDisplayFromResolveInfo(ResolveInfo p) {
        String appName = p.loadLabel(getPackageManager()).toString();
        String packageName = p.activityInfo.packageName;
//        Log.i("ResolvePackage",packageName==null?"null":packageName);

        Drawable icon = null;

        try {
            icon = p.activityInfo.loadIcon(getPackageManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AppDisplayer(appName, packageName, icon);
    }

    @Override
    protected void onDestroy() {
        Log.i("MainOnDestroy", "is called");
        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
        super.onDestroy();
    }

    //    @Override
//    protected void onStop() {
//        Log.i("MainOnStop","is called");
//        Intent intent = new Intent("com.dkv.applocker.controller.service_and_state_pattern");
//        sendBroadcast(intent);
//        super.onStop();
//    }

    public void unlockDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.dialog_unlock, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        final EditText txtPassword = view.findViewById(R.id.txtPasswordRe);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pressOkButton(txtPassword.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getToHomeScreen();
            }
        });
        builder.show();
    }

    private void pressOkButton(String pass) {
        //Compare password in database and user's password
        Password p = new Password(pass,this);
        if (p.isMatchedWithTokenInDB()) {
            //if match, close activity
            ServiceController locker = ServiceController.getOurInstance();
            locker.setCurrentState(locker.getUnlockedState());
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

}