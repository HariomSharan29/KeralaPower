package com.techlabs.apdcl.Utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class DayNightTheme extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        boolean isDebuggable = (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!isDebuggable);
    }
}

