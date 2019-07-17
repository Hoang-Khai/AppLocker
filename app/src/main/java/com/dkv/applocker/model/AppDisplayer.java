package com.dkv.applocker.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AppDisplayer implements Serializable {
    private String packageName;
    private Drawable icon;

    public AppDisplayer(String packageName, Drawable icon) {
        this.packageName = packageName;
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
