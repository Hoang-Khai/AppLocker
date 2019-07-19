package com.dkv.applocker.controller;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;

import java.util.Collections;
import java.util.List;

public class TopActivityProcessService extends Service {
    public TopActivityProcessService() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        String activityOnTop = ar.topActivity.getClassName();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//        PackageManager packageManager = getPackageManager();
//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
//        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
//        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
//        for (int i = 0; i < packs.size(); i++) {
//            PackageInfo p = packs.get(i);
//            ApplicationInfo a = p.applicationInfo;
//            // skip system apps if they shall not be included
//            if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
//                continue;
//            }
//            appList.add(p.packageName);
//        }


}
