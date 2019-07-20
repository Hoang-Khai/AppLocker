package com.dkv.applocker.activity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dkv.applocker.R;
import com.dkv.applocker.controller.AppListAdapter;
import com.dkv.applocker.exception.WrongPasswordException;
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
        //https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
        //https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start
        //https://stackoverflow.com/questions/19852069/blocking-android-apps-programmatically/19852713#comment29526195_19852713
        //https://stackoverflow.com/questions/8061179/broadcast-receiver-to-detect-application-start
        //https://github.com/savvisingh/AppLock
        //https://github.com/SubhamTyagi/AppLock
        //https://github.com/mattsilber/applock

        apps = new ArrayList<>();
        getAllApp();
//        Password p = new Password("1234",getApplicationContext());
//        try {
//            p.changePasswordInSQlite("");
//        } catch (WrongPasswordException e) {
//            e.printStackTrace();
//        }

        btnSetting = findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,SetPasswordActivity.class);
                startActivityForResult(intent,100);
            }
        });

        appListView = findViewById(R.id.appListView);
        appListView.setAdapter(new AppListAdapter(MainActivity.this,apps));
        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("ItemClick",parent.getItemAtPosition(position).getClass().toString());
                changeAppState(parent, view, position, id);
            }
        });
    }

    private void changeAppState(AdapterView<?> parent, View view, int position, long id) {
        AppDisplayer selected = (AppDisplayer)parent.getItemAtPosition(position);
        String selectedPackage = selected.getPackageName();
//        Log.i("TouchPackage",selectedPackage);
        LockedAppList lockedAppList = new LockedAppList(getApplicationContext());
        if (lockedAppList.hasForePackageLocked(selectedPackage)) {
            lockedAppList.deletePackageFromDB(getApplicationContext(),selectedPackage);
        } else {
            lockedAppList.writePackageToDB(getApplicationContext(),selectedPackage);
        }

        appListView.setAdapter(new AppListAdapter(MainActivity.this,apps));
    }

    //Get all application on device
    public void getAllApp() {
        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
//        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        int appListNumber = appList.size();
        for (int i = 0; i < appListNumber; i++) {
            ResolveInfo p = appList.get(i);
//            ApplicationInfo a = p.filter.;

            // skip system apps if they shall not be included
//            if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
//                continue;
//            }
//            appList.add(p.packageName);
//            Log.w("package",p.packageName); //Log test package

            //Get icon from app
            AppDisplayer app = getAppDisplayerFromPackageInfo(p);
            apps.add(app);
        }
    }

    private AppDisplayer getAppDisplayerFromPackageInfo(ResolveInfo p) {
        String appName = p.loadLabel(getPackageManager()).toString();
//        String appName = "Test";
        String packageName = p.activityInfo.packageName;
//        Log.i("ResolvePackage",packageName==null?"null":packageName);

        Drawable icon=null;
        try {
            icon = p.activityInfo.loadIcon(getPackageManager());
//            icon = getPackageManager().getApplicationIcon(packageName);
//                ImageView v = findViewById(R.id.imgApp);
//                v.setImageDrawable(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AppDisplayer(appName, packageName,icon);
    }
}